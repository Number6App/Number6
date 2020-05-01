package dev.number6.slackreader.core

import assertk.assertThat
import assertk.assertions.containsOnly
import assertk.assertions.isEqualTo
import assertk.assertions.isIn
import com.amazonaws.services.lambda.runtime.LambdaLogger
import com.google.gson.Gson
import dev.number6.db.model.ChannelComprehensionSummary
import dev.number6.message.ChannelMessages
import dev.number6.slackreader.SlackReader
import dev.number6.slackreader.dagger.DaggerTestSlackReaderComponent
import dev.number6.slackreader.dagger.RecordingSlackReaderAdaptor
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.util.*

internal class SlackReaderIntegrationTest {
    private val gson = Gson()
    private val logger: LambdaLogger = object : LambdaLogger {
        override fun log(message: String?) {
            println(message)
        }

        override fun log(message: ByteArray?) {
            println(Arrays.toString(message))
        }
    }
    private val testee = DaggerTestSlackReaderComponent.create()

    @Test
    fun handleEventWithoutDate() {
        val clock = testee.getClock()
        val triggerEvent: Map<String, Any> = mapOf()
        testEventComprehensionDate(triggerEvent, LocalDate.now(clock))
    }

    @Test
    fun handleEventWithDate() {
        val clock = testee.getClock()
        val triggerEvent: Map<String, Any> = mapOf(SlackReader.COMPREHENSION_DATE_FIELD_NAME to LocalDate.now(clock).toString())
        testEventComprehensionDate(triggerEvent, LocalDate.now(clock))
    }

    private fun testEventComprehensionDate(event: Map<String, Any>?, comprehensionDate: LocalDate?) {
        var triggerEvent = event
        val reader = testee.getSlackReaderPort() as RecordingSlackReaderAdaptor
        val fakeDynamo = testee.getFakeAmazonDynamoClient()
        val fakeSns = testee.getFakeAmazonSns()
        triggerEvent = triggerEvent?.plus(SlackReader.COMPREHENSION_DATE_FIELD_NAME to comprehensionDate.toString())
                ?: mapOf()
        testee.handler().handle(triggerEvent, logger)
        val savedChannelSummaries = fakeDynamo.getSavedObjects().map { o -> o as ChannelComprehensionSummary }

        savedChannelSummaries.forEach { s -> assertThat(s.comprehensionDate).isEqualTo(comprehensionDate) }

        val savedNames: List<String> = savedChannelSummaries.map { ccs -> ccs.channelName }
        val readerNames = reader.getChannelNames().toTypedArray()
        assertThat(savedNames).containsOnly(*readerNames)

        val channelMessages = fakeSns.published
                .map { p: String? -> gson.fromJson(p, ChannelMessages::class.java) }

        channelMessages.forEach { cm -> assertThat(cm.channelName).isIn(*readerNames) }
        channelMessages.forEach { cm -> assertThat(cm.messages).containsOnly(*reader.getMessagesForChannelName(cm.channelName).toTypedArray()) }
    }
}