package dev.number6.slackreader

import assertk.assertThat
import assertk.assertions.containsOnly
import assertk.assertions.isNotNull
import com.amazonaws.services.lambda.runtime.LambdaLogger
import com.google.gson.Gson
import dev.number6.slack.CallResponse
import dev.number6.slack.adaptor.SlackClientAdaptor
import dev.number6.slack.port.HttpPort
import dev.number6.slackreader.adaptor.SlackReaderAdaptor
import dev.number6.slackreader.generate.SlackReaderRDG
import dev.number6.slackreader.model.Channel
import dev.number6.slackreader.model.ChannelHistoryResponse
import dev.number6.slackreader.model.JoinChannelResponse
import dev.number6.slackreader.model.Message
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyOrder
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import uk.org.fyodor.generators.RDG
import java.time.LocalDate
import java.time.ZoneOffset

@ExtendWith(MockKExtension::class)
internal class SlackReaderAdaptorTest {
    private val gson = Gson()

    private val httpClient = mockk<HttpPort>()

    private val logger = mockk<LambdaLogger>(relaxUnitFun = true)
    private val testee = SlackReaderAdaptor(httpClient)

    @Test
    fun getListOfSlackChannels() {
        val channelsListResponse = SlackReaderRDG.channelsListResponse().next()
        val expectedChannels = channelsListResponse.channels
        prepareHttpGetCallMock(channelsListResponse)
        val channels = testee.getChannelList(logger)
        verify(exactly = 1) { httpClient.get(SlackClientAdaptor.CHANNEL_LIST_URL, logger) }

        assertThat(channels).isNotNull()
                .containsOnly(*expectedChannels.toTypedArray())
    }

    @Test
    fun joinChannelAndGetMessagesForChannelOnDate() {
        val expectedChannelHistory = SlackReaderRDG.channelHistoryResponse().next()
        val channel = SlackReaderRDG.channel().next()
        val messagesDate = LocalDate.now()
        val channelHistoryUrl = formatChannelHistoryUrl(channel, messagesDate)
        val joinChannelUrl = formatJoinChannelUrl(channel)
        prepareHttpGetCallMock(expectedChannelHistory)
        prepareHttpPostCallMock(JoinChannelResponse.Companion.ok())
        val messages = testee.getMessagesForChannelOnDate(channel, messagesDate, logger)
        verifyOrder {
            httpClient.post(joinChannelUrl, "", logger)
            httpClient.get(channelHistoryUrl, logger)
        }
        assertThat(messages).containsOnly(*expectedChannelHistory.messages.toTypedArray())
    }

    @Test
    fun excludeMessagesWithNoText() {
        val expectedChannelHistory = SlackReaderRDG.channelHistoryResponse().next()
        val emptyMessages = RDG.list(SlackReaderRDG.message()).next()
                .map { m -> Message.empty(m) }
        val nullMessages = RDG.list(SlackReaderRDG.message()).next()
                .map { m -> Message.nullText(m) }
        val spaceMessages = RDG.list(SlackReaderRDG.message()).next()
                .map { m -> Message.nullText(m) }
        val responseMessages = expectedChannelHistory.messages
                .plus(emptyMessages)
                .plus(nullMessages)
                .plus(spaceMessages)
        val response = ChannelHistoryResponse(true,
                responseMessages,
                false,
                expectedChannelHistory.oldest,
                expectedChannelHistory.latest)
        val channel = SlackReaderRDG.channel().next()
        val messagesDate = LocalDate.now()
        val url = formatChannelHistoryUrl(channel, messagesDate)
        prepareHttpGetCallMock(response)
        prepareHttpPostCallMock(JoinChannelResponse.ok())
        val messages = testee.getMessagesForChannelOnDate(channel, messagesDate, logger)
        verify { httpClient.get(url, logger) }
        assertThat(messages).containsOnly(*expectedChannelHistory.messages.toTypedArray())
    }

    @Test
    fun excludeMessagesFromBots() {
        val expectedChannelHistory = SlackReaderRDG.channelHistoryResponse().next()
        val botMessages = RDG.list(SlackReaderRDG.message()).next()
                .map { m -> Message.fromBot(m) }
        val responseMessages = expectedChannelHistory.messages
                .plus(botMessages)
        val response = ChannelHistoryResponse(true,
                responseMessages,
                false,
                expectedChannelHistory.oldest,
                expectedChannelHistory.latest)
        val channel = SlackReaderRDG.channel().next()
        val messagesDate = LocalDate.now()
        val url = formatChannelHistoryUrl(channel, messagesDate)
        prepareHttpGetCallMock(response)
        prepareHttpPostCallMock(JoinChannelResponse.ok())
        val messages = testee.getMessagesForChannelOnDate(channel, messagesDate, logger)
        verify { httpClient.get(url, logger) }
        assertThat(messages).containsOnly(*expectedChannelHistory.messages.toTypedArray())
    }

    private fun formatChannelHistoryUrl(channel: Channel, messagesDate: LocalDate): String {
        return String.format(SlackClientAdaptor.CHANNEL_HISTORY_URL, channel.id, messagesDate.atStartOfDay().toEpochSecond(ZoneOffset.UTC),
                messagesDate.plusDays(1).atStartOfDay().toEpochSecond(ZoneOffset.UTC))
    }

    private fun formatJoinChannelUrl(channel: Channel): String {
        return String.format(SlackClientAdaptor.JOIN_CHANNEL_URL, channel.id)
    }

    private fun prepareHttpGetCallMock(body: Any?) {
        every { httpClient.get(any(), any()) } returns CallResponse(gson.toJson(body))
    }

    private fun prepareHttpPostCallMock(body: Any) {
        every { httpClient.post(any(), any(), any()) } returns CallResponse(gson.toJson(body))
    }
}