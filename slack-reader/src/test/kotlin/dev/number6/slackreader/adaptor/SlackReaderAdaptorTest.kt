package dev.number6.slackreader.adaptor

import assertk.assertThat
import assertk.assertions.containsOnly
import assertk.assertions.isNotNull
import com.amazonaws.services.lambda.runtime.LambdaLogger
import dev.number6.slack.model.ChannelHistoryResponse
import dev.number6.slack.model.ChannelsListResponse
import dev.number6.slack.model.JoinChannelResponse
import dev.number6.slack.model.Message
import dev.number6.slack.port.SlackPort
import dev.number6.slackreader.generate.SlackReaderRDG
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verifyOrder
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import uk.org.fyodor.generators.RDG
import java.time.LocalDate

@ExtendWith(MockKExtension::class)
internal class SlackReaderAdaptorTest {

    private val slackPort = mockk<SlackPort>()

    private val logger = mockk<LambdaLogger>(relaxUnitFun = true)
    private val testee = SlackReaderAdaptor(slackPort)

    @Test
    fun getListOfSlackChannels() {
        val expectedChannels = SlackReaderRDG.channelsListResponse().next().channels
        every { slackPort.getChannelList(logger) } returns ChannelsListResponse(true, expectedChannels)

        val channels = testee.getChannelList(logger)

        assertThat(channels).isNotNull()
                .containsOnly(*expectedChannels.toTypedArray())
    }

    @Test
    fun joinChannelAndGetMessagesForChannelOnDate() {
        val expectedChannelHistory = SlackReaderRDG.channelHistoryResponse().next()
        val channel = SlackReaderRDG.channel().next()
        val messagesDate = LocalDate.now()

        every { slackPort.joinChannel(channel, logger) } returns JoinChannelResponse.ok()
        every { slackPort.getMessagesForChannelOnDate(channel, messagesDate, logger) } returns expectedChannelHistory

        val messages = testee.getMessagesForChannelOnDate(channel, messagesDate, logger)
        verifyOrder {
            slackPort.joinChannel(channel, logger)
            slackPort.getMessagesForChannelOnDate(channel, messagesDate, logger)
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

        every { slackPort.joinChannel(channel, logger) } returns JoinChannelResponse.ok()
        every { slackPort.getMessagesForChannelOnDate(channel, messagesDate, logger) } returns response

        val messages = testee.getMessagesForChannelOnDate(channel, messagesDate, logger)

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

        every { slackPort.joinChannel(channel, logger) } returns JoinChannelResponse.ok()
        every { slackPort.getMessagesForChannelOnDate(channel, messagesDate, logger) } returns response

        val messages = testee.getMessagesForChannelOnDate(channel, messagesDate, logger)

        assertThat(messages).containsOnly(*expectedChannelHistory.messages.toTypedArray())
    }
}