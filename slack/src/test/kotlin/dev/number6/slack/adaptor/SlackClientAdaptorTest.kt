package dev.number6.slack.adaptor

import assertk.assertThat
import assertk.assertions.*
import com.amazonaws.services.lambda.runtime.LambdaLogger
import com.google.gson.Gson
import dev.number6.slack.generate.SlackRDG
import dev.number6.slack.model.CallResponse
import dev.number6.slack.model.Channel
import dev.number6.slack.model.JoinChannelResponse
import dev.number6.slack.port.HttpPort
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import uk.org.fyodor.generators.Generator
import uk.org.fyodor.generators.RDG
import java.time.LocalDate
import java.time.ZoneOffset
import java.util.*

@ExtendWith(MockKExtension::class)
internal class SlackClientAdaptorTest {
    var logger: LambdaLogger = mockk(relaxUnitFun = true)
    var http: HttpPort = mockk()

    val testee = SlackClientAdaptor(http)

    private val gson = Gson()
    private var testResponseObject: TestResponseObject? = null
    private val testObjectGenerator = TestObjectGenerator()

    @BeforeEach
    fun setup() {
        testResponseObject = testObjectGenerator.next()
    }

    @Test
    fun postMessageToSlackChannel() {
        every { http.post(any(), any(), any()) } returns CallResponse(gson.toJson(testResponseObject))
        val content = RDG.string().next()
        testee.postMessageToChannel(content, logger)
        verify {
            http.post(SlackClientAdaptor.POST_MESSAGE_URL,
                    content,
                    logger)
        }
    }

    @Test
    fun joinChannelSuccess() {
        val channel = Channel("channel-id", "channel-name")
        every { http.post(String.format(SlackClientAdaptor.JOIN_CHANNEL_URL, channel.id), "", logger) } returns
                CallResponse(gson.toJson(JoinChannelResponse.ok()))
        val response = testee.joinChannel(channel, logger)

        assertThat(response.ok).isTrue()
    }

    @Test
    fun joinChannelFailure() {
        val channel = Channel("channel-id", "channel-name")
        every { http.post(String.format(SlackClientAdaptor.JOIN_CHANNEL_URL, channel.id), "", logger) } returns
                CallResponse(gson.toJson(JoinChannelResponse.failed()))
        val response = testee.joinChannel(channel, logger)

        assertThat(response.ok).isFalse()
    }

    @Test
    fun joinChannelException() {
        val channel = Channel("channel-id", "channel-name")
        every { http.post(String.format(SlackClientAdaptor.JOIN_CHANNEL_URL, channel.id), "", logger) } returns
                CallResponse(UnsupportedOperationException("can't reach Slack"))
        val response = testee.joinChannel(channel, logger)

        assertThat(response.ok).isFalse()
    }

    @Test
    fun getChannelList() {
        val channelsListResponse = SlackRDG.channelsListResponse().next()
        val expectedChannels = channelsListResponse.channels
        every { http.get(SlackClientAdaptor.CHANNEL_LIST_URL, logger) } returns CallResponse(gson.toJson(channelsListResponse))

        val channels = testee.getChannelList(logger)

        assertThat(channels.channels).isNotNull()
                .containsOnly(*expectedChannels.toTypedArray())
    }

    @Test
    fun getChannelMessagesOnDate(){
        val expectedChannelHistory = SlackRDG.channelHistoryResponse(true).next()
        val channel = SlackRDG.channel().next()
        val messagesDate = LocalDate.now()

        every {http.get(formatChannelHistoryUrl(channel, messagesDate), logger)} returns CallResponse(gson.toJson(expectedChannelHistory))
        val response = testee.getMessagesForChannelOnDate(channel, messagesDate, logger)

        assertThat(response.ok).isNotNull().isTrue()
    }

    private fun formatChannelHistoryUrl(channel: Channel, messagesDate: LocalDate): String {
        return String.format(SlackClientAdaptor.CHANNEL_HISTORY_URL, channel.id, messagesDate.atStartOfDay().toEpochSecond(ZoneOffset.UTC),
                messagesDate.plusDays(1).atStartOfDay().toEpochSecond(ZoneOffset.UTC))
    }

    @Test
    fun requestWithResponseObject() {
        every { http.get(any(), any()) } returns CallResponse(gson.toJson(testResponseObject))
        val response = testee.getSlackResponse(SlackClientAdaptor.CHANNEL_LIST_URL, TestResponseObject::class.java, logger)
        assertThat(response.isPresent).isTrue()
        assertThat(response.get()).isEqualTo(testResponseObject)
    }

    @Test
    fun postWithBodyNoResponse() {
        every { http.post(any(), any(), any()) } returns CallResponse(gson.toJson(testResponseObject))
        val response = testee.postToSlackNoResponse(SlackClientAdaptor.CHANNEL_HISTORY_URL, logger, "BODY")
        assertThat(response.isPresent).isFalse()
    }

    @Test
    fun postWithBodyAndResponse() {
        every { http.post(any(), any(), any()) } returns CallResponse(gson.toJson(testResponseObject))
        val response = testee.callSlack(SlackClientAdaptor.CHANNEL_HISTORY_URL, "BODY", TestResponseObject::class.java, logger)
        assertThat(response.isPresent).isTrue()
        assertThat(response.get()).isEqualTo(testResponseObject)
    }

    class TestResponseObject internal constructor(private val field1: Int, private val field2: String, private val field3: Double) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other == null || javaClass != other.javaClass) return false
            val that = other as TestResponseObject
            return field1 == that.field1 &&
                    field2 == that.field2 &&
                    field3 == that.field3
        }

        override fun hashCode(): Int {
            return Objects.hash(field1, field2, field3)
        }

    }

    internal class TestObjectGenerator : Generator<TestResponseObject> {
        override fun next(): TestResponseObject {
            return TestResponseObject(RDG.integer(999).next(),
                    RDG.string(30).next(),
                    RDG.doubleVal(9999.0).next())
        }
    }
}