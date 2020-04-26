package dev.number6.slackreader

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.amazonaws.services.sns.AmazonSNS
import com.amazonaws.services.sns.model.PublishRequest
import com.google.gson.Gson
import dev.number6.message.ChannelMessages
import dev.number6.slackreader.adaptor.EnvironmentVariableSlackReaderConfigurationAdapter
import dev.number6.slackreader.adaptor.SnsNotificationAdaptor
import dev.number6.slackreader.port.NotificationPort
import dev.number6.slackreader.port.SlackReaderConfigurationPort
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import uk.org.fyodor.generators.RDG
import java.time.LocalDate

internal class SnsNotificationAdaptorTest {
    private val gson = Gson()
    private val config: SlackReaderConfigurationPort = mockk<EnvironmentVariableSlackReaderConfigurationAdapter>(relaxUnitFun = true)
    private val sns = mockk<AmazonSNS>(relaxed = true)
    private var testee: NotificationPort? = null

    @BeforeEach
    fun setup() {
        every { config.getTopicArn() } returns TOPIC_ARN
        testee = SnsNotificationAdaptor(config, sns)
    }

    @Test
    fun sendSerializedChannelMessagesToSns() {
        val messages = ChannelMessages(RDG.string().next(), RDG.list(RDG.string()).next(), LocalDate.now())
        testee?.broadcast(messages)
        val slot = slot<PublishRequest>()
        verify {
            sns.publish(capture(slot))
        }
        assertThat(slot.captured.topicArn).isEqualTo(TOPIC_ARN)

        assertThat(gson.fromJson(slot.captured.message, ChannelMessages::class.java)).isEqualTo(messages)
    }

    companion object {
        private const val TOPIC_ARN: String = "Topic-Arn"
    }
}