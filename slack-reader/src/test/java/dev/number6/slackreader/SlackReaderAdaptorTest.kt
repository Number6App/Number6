package dev.number6.slackreader

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
import dev.number6.slackreader.port.SlackPort
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import uk.org.fyodor.generators.RDG
import java.time.LocalDate
import java.time.ZoneOffset
import java.util.*
import java.util.function.Function
import java.util.stream.Collectors

@ExtendWith(MockitoExtension::class)
@Disabled("work out what to do with Mockito")
internal class SlackReaderAdaptorTest {
    private val gson: Gson? = Gson()

    @Mock
    var httpClient: HttpPort? = null

    @Mock
    var logger: LambdaLogger? = null
    private var testee: SlackPort? = null

//    @BeforeEach
//    fun setup() {
//        testee = SlackReaderAdaptor(httpClient)
//    }

//    @Test
//    fun getListOfSlackChannels() {
//        val channelsListResponse = SlackReaderRDG.channelsListResponse().next()
//        val expectedChannels = channelsListResponse.channels
//        prepareHttpGetCallMock(channelsListResponse)
//        val channels = testee.getChannelList(logger)
//        Mockito.verify(httpClient)[SlackClientAdaptor.CHANNEL_LIST_URL, logger]
//        Assertions.assertThat(channels).isNotNull
//        Assertions.assertThat(channels).hasSize(expectedChannels.size)
//        Assertions.assertThat(channels).containsExactlyInAnyOrderElementsOf(expectedChannels)
//    }

//    @Test
//    fun joinChannelAndGetMessagesForChannelOnDate() {
//        val expectedChannelHistory = SlackReaderRDG.channelHistoryResponse().next()
//        val channel = SlackReaderRDG.channel().next()
//        val messagesDate = LocalDate.now()
//        val channelHistoryUrl = formatChannelHistoryUrl(channel, messagesDate)
//        val joinChannelUrl = formatJoinChannelUrl(channel)
//        prepareHttpGetCallMock(expectedChannelHistory)
//        prepareHttpPostCallMock(JoinChannelResponse.Companion.ok())
//        val messages = testee.getMessagesForChannelOnDate(channel, messagesDate, logger)
//        Mockito.verify(httpClient)[channelHistoryUrl, logger]
//        Mockito.verify(httpClient).post(joinChannelUrl, "", logger)
//        Assertions.assertThat(messages).containsExactlyInAnyOrderElementsOf(expectedChannelHistory.messages)
//    }
//
//    @Test
//    fun excludeMessagesWithNoText() {
//        val expectedChannelHistory = SlackReaderRDG.channelHistoryResponse().next()
//        val emptyMessages: MutableCollection<Message?>? = RDG.list(SlackReaderRDG.message()).next()
//                .stream()
//                .map(Function<Message?, Message?> { message: Message? -> Message.Companion.empty(message) })
//                .collect(Collectors.toList())
//        val nullMessages: MutableCollection<Message?>? = RDG.list(SlackReaderRDG.message()).next()
//                .stream()
//                .map(Function<Message?, Message?> { message: Message? -> Message.Companion.nullText(message) })
//                .collect(Collectors.toList())
//        val spaceMessages: MutableCollection<Message?>? = RDG.list(SlackReaderRDG.message()).next()
//                .stream()
//                .map(Function<Message?, Message?> { message: Message? -> Message.Companion.nullText(message) })
//                .collect(Collectors.toList())
//        val responseMessages = ArrayList(expectedChannelHistory.messages)
//        responseMessages.addAll(emptyMessages)
//        responseMessages.addAll(nullMessages)
//        responseMessages.addAll(spaceMessages)
//        val response = ChannelHistoryResponse(true,
//                responseMessages,
//                false,
//                expectedChannelHistory.oldest,
//                expectedChannelHistory.latest)
//        val channel = SlackReaderRDG.channel().next()
//        val messagesDate = LocalDate.now()
//        val url = formatChannelHistoryUrl(channel, messagesDate)
//        prepareHttpGetCallMock(response)
//        prepareHttpPostCallMock(JoinChannelResponse.Companion.ok())
//        val messages = testee.getMessagesForChannelOnDate(channel, messagesDate, logger)
//        Mockito.verify(httpClient)[url, logger]
//        Assertions.assertThat(messages).containsExactlyInAnyOrderElementsOf(expectedChannelHistory.messages)
//    }
//
//    @Test
//    fun excludeMessagesFromBots() {
//        val expectedChannelHistory = SlackReaderRDG.channelHistoryResponse().next()
//        val botMessages: MutableCollection<Message?>? = RDG.list(SlackReaderRDG.message()).next()
//                .stream()
//                .map(Function<Message?, Message?> { message: Message? -> Message.Companion.fromBot(message) })
//                .collect(Collectors.toList())
//        val responseMessages = ArrayList(expectedChannelHistory.messages)
//        responseMessages.addAll(botMessages)
//        val response = ChannelHistoryResponse(true,
//                responseMessages,
//                false,
//                expectedChannelHistory.oldest,
//                expectedChannelHistory.latest)
//        val channel = SlackReaderRDG.channel().next()
//        val messagesDate = LocalDate.now()
//        val url = formatChannelHistoryUrl(channel, messagesDate)
//        prepareHttpGetCallMock(response)
//        prepareHttpPostCallMock(JoinChannelResponse.Companion.ok())
//        val messages = testee.getMessagesForChannelOnDate(channel, messagesDate, logger)
//        Mockito.verify(httpClient)[url, logger]
//        Assertions.assertThat(messages).containsExactlyInAnyOrderElementsOf(expectedChannelHistory.messages)
//    }
//
//    private fun formatChannelHistoryUrl(channel: Channel?, messagesDate: LocalDate?): String? {
//        return String.format(SlackClientAdaptor.CHANNEL_HISTORY_URL, channel.getId(), messagesDate.atStartOfDay().toEpochSecond(ZoneOffset.UTC),
//                messagesDate.plusDays(1).atStartOfDay().toEpochSecond(ZoneOffset.UTC))
//    }
//
//    private fun formatJoinChannelUrl(channel: Channel?): String? {
//        return String.format(SlackClientAdaptor.JOIN_CHANNEL_URL, channel.getId())
//    }
//
//    private fun prepareHttpGetCallMock(body: Any?) {
//        Mockito.`when`(httpClient.get(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(CallResponse(gson.toJson(body)))
//    }
//
//    private fun prepareHttpPostCallMock(body: Any?) {
//        Mockito.`when`(httpClient.post(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(CallResponse(gson.toJson(body)))
//    }
}