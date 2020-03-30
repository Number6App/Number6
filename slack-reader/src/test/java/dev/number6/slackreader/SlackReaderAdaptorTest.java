package dev.number6.slackreader;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.google.gson.Gson;
import dev.number6.slack.CallResponse;
import dev.number6.slack.adaptor.SlackClientAdaptor;
import dev.number6.slack.port.HttpPort;
import dev.number6.slackreader.adaptor.SlackReaderAdaptor;
import dev.number6.slackreader.generate.SlackReaderRDG;
import dev.number6.slackreader.model.*;
import dev.number6.slackreader.port.SlackPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SlackReaderAdaptorTest {

    private final Gson gson = new Gson();

    @Mock
    HttpPort httpClient;
    @Mock
    LambdaLogger logger;

    private SlackPort testee;

    @BeforeEach
    void setup() {
        testee = new SlackReaderAdaptor(httpClient);
    }

    @Test
    void getListOfSlackChannels() {

        ChannelsListResponse channelsListResponse = SlackReaderRDG.channelsListResponse().next();
        Collection<Channel> expectedChannels = channelsListResponse.getChannels();
        prepareHttpGetCallMock(channelsListResponse);

        Collection<Channel> channels = testee.getChannelList(logger);

        verify(httpClient).get(SlackClientAdaptor.CHANNEL_LIST_URL, logger);

        assertThat(channels).isNotNull();
        assertThat(channels).hasSize(expectedChannels.size());
        assertThat(channels).containsExactlyInAnyOrderElementsOf(expectedChannels);
    }

    @Test
    void joinChannelAndGetMessagesForChannelOnDate() {

        ChannelHistoryResponse expectedChannelHistory = SlackReaderRDG.channelHistoryResponse().next();
        Channel channel = SlackReaderRDG.channel().next();
        LocalDate messagesDate = LocalDate.now();
        String channelHistoryUrl = formatChannelHistoryUrl(channel, messagesDate);
        String joinChannelUrl = formatJoinChannelUrl(channel);

        prepareHttpGetCallMock(expectedChannelHistory);
        prepareHttpPostCallMock(JoinChannelResponse.ok());

        Collection<Message> messages = testee.getMessagesForChannelOnDate(channel, messagesDate, logger);

        verify(httpClient).get(channelHistoryUrl, logger);
        verify(httpClient).post(joinChannelUrl, "", logger);

        assertThat(messages).containsExactlyInAnyOrderElementsOf(expectedChannelHistory.getMessages());
    }

    @Test
    void excludeMessagesWithNoText() {
        ChannelHistoryResponse expectedChannelHistory = SlackReaderRDG.channelHistoryResponse().next();
        Collection<Message> emptyMessages = SlackReaderRDG.list(SlackReaderRDG.message()).next()
                .stream()
                .map(Message::empty)
                .collect(Collectors.toList());
        Collection<Message> nullMessages = SlackReaderRDG.list(SlackReaderRDG.message()).next()
                .stream()
                .map(Message::nullText)
                .collect(Collectors.toList());
        Collection<Message> spaceMessages = SlackReaderRDG.list(SlackReaderRDG.message()).next()
                .stream()
                .map(Message::nullText)
                .collect(Collectors.toList());
        var responseMessages = new ArrayList<>(expectedChannelHistory.getMessages());
        responseMessages.addAll(emptyMessages);
        responseMessages.addAll(nullMessages);
        responseMessages.addAll(spaceMessages);
        ChannelHistoryResponse response = new ChannelHistoryResponse(true,
                responseMessages,
                false,
                expectedChannelHistory.getOldest(),
                expectedChannelHistory.getLatest());

        Channel channel = SlackReaderRDG.channel().next();
        LocalDate messagesDate = LocalDate.now();
        String url = formatChannelHistoryUrl(channel, messagesDate);

        prepareHttpGetCallMock(response);
        prepareHttpPostCallMock(JoinChannelResponse.ok());

        Collection<Message> messages = testee.getMessagesForChannelOnDate(channel, messagesDate, logger);

        verify(httpClient).get(url, logger);

        assertThat(messages).containsExactlyInAnyOrderElementsOf(expectedChannelHistory.getMessages());
    }

    @Test
    void excludeMessagesFromBots() {
        ChannelHistoryResponse expectedChannelHistory = SlackReaderRDG.channelHistoryResponse().next();
        Collection<Message> botMessages = SlackReaderRDG.list(SlackReaderRDG.message()).next()
                .stream()
                .map(Message::fromBot)
                .collect(Collectors.toList());
        var responseMessages = new ArrayList<>(expectedChannelHistory.getMessages());
        responseMessages.addAll(botMessages);
        ChannelHistoryResponse response = new ChannelHistoryResponse(true,
                responseMessages,
                false,
                expectedChannelHistory.getOldest(),
                expectedChannelHistory.getLatest());

        Channel channel = SlackReaderRDG.channel().next();
        LocalDate messagesDate = LocalDate.now();
        String url = formatChannelHistoryUrl(channel, messagesDate);

        prepareHttpGetCallMock(response);
        prepareHttpPostCallMock(JoinChannelResponse.ok());

        Collection<Message> messages = testee.getMessagesForChannelOnDate(channel, messagesDate, logger);

        verify(httpClient).get(url, logger);

        assertThat(messages).containsExactlyInAnyOrderElementsOf(expectedChannelHistory.getMessages());
    }

    private String formatChannelHistoryUrl(Channel channel, LocalDate messagesDate) {
        return String.format(SlackClientAdaptor.CHANNEL_HISTORY_URL, channel.getId(), messagesDate.atStartOfDay().toEpochSecond(ZoneOffset.UTC),
                messagesDate.plusDays(1).atStartOfDay().toEpochSecond(ZoneOffset.UTC));
    }

    private String formatJoinChannelUrl(Channel channel) {
        return String.format(SlackClientAdaptor.JOIN_CHANNEL_URL, channel.getId());
    }

    private void prepareHttpGetCallMock(Object body) {
        when(httpClient.get(any(), any())).thenReturn(new CallResponse(gson.toJson(body)));
    }

    private void prepareHttpPostCallMock(Object body) {
        when(httpClient.post(any(), any(), any())).thenReturn(new CallResponse(gson.toJson(body)));
    }
}