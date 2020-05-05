package dev.number6.slack.adaptor

import assertk.assertThat
import assertk.assertions.containsOnly
import assertk.assertions.isFalse
import assertk.assertions.isNotNull
import assertk.assertions.isTrue
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
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import uk.org.fyodor.generators.RDG
import java.time.LocalDate
import java.time.ZoneOffset

@ExtendWith(MockKExtension::class)
internal class SlackClientAdaptorTest {
    var logger: LambdaLogger = mockk(relaxUnitFun = true)
    private var http: HttpPort = mockk()

    val testee = SlackClientAdaptor(http)

    private val gson = Gson()

    @Test
    fun postMessageToSlackChannel() {
        every { http.post(any(), any(), any()) } returns CallResponse("")
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
    fun getChannelMessagesOnDate() {
        val expectedChannelHistory = SlackRDG.channelHistoryResponse(true).next()
        val channel = SlackRDG.channel().next()
        val messagesDate = LocalDate.now()

        every { http.get(formatChannelHistoryUrl(channel, messagesDate), logger) } returns CallResponse(gson.toJson(expectedChannelHistory))
        val response = testee.getMessagesForChannelOnDate(channel, messagesDate, logger)

        assertThat(response.ok).isNotNull().isTrue()
    }

    private fun formatChannelHistoryUrl(channel: Channel, messagesDate: LocalDate): String {
        return String.format(SlackClientAdaptor.CHANNEL_HISTORY_URL, channel.id, messagesDate.atStartOfDay().toEpochSecond(ZoneOffset.UTC),
                messagesDate.plusDays(1).atStartOfDay().toEpochSecond(ZoneOffset.UTC))
    }
}