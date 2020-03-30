package dev.number6.slackreader.core;

import dev.number6.slackreader.SlackService;
import dev.number6.slackreader.model.Channel;
import dev.number6.slackreader.model.Message;
import dev.number6.slackreader.model.WorkspaceMessages;
import dev.number6.slackreader.port.SlackReaderConfigurationPort;
import dev.number6.slackreader.port.SlackPort;
import dev.number6.slackreader.generate.SlackReaderRDG;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.org.fyodor.generators.Generator;
import uk.org.fyodor.range.Range;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SlackServiceTest {

    @Mock
    private LambdaLogger logger;
    @Mock
    private SlackPort slackPort;
    @Mock
    private SlackReaderConfigurationPort config;

    private final Generator<Set<Channel>> channelsGenerator = SlackReaderRDG.channels(Range.closed(5, 20));
    private final Generator<List<Message>> messagesGenerator = SlackReaderRDG.list(SlackReaderRDG.message(), Range.closed(5, 50));
    private SlackService testee;

    @BeforeEach
    void setup() {
        testee = new SlackService(slackPort, config);
    }

    @Test
    void getMessagesOnDate() {

        Set<Channel> channels = channelsGenerator.next();
        Map<String, List<Message>> expectedMessages = channels.stream().collect(Collectors.toMap(Channel::getName, c -> messagesGenerator.next()));

        when(slackPort.getChannelList(logger)).thenReturn(channels);
        when(slackPort.getMessagesForChannelOnDate(any(), any(), any())).then(i -> expectedMessages.get(((Channel) i.getArgument(0)).getName()));

        WorkspaceMessages messages = testee.getMessagesOnDate(LocalDate.now(), logger);

        List<String> channelNames = channels.stream().map(Channel::getName).collect(Collectors.toList());
        assertThat(messages.getChannelNames()).containsExactlyInAnyOrderElementsOf(channelNames);

        messages.getChannelNames().forEach(name ->
                assertThat(messages.getMessagesForChannel(name))
                        .containsExactlyInAnyOrderElementsOf(expectedMessages.get(name).stream().map(Message::getText).collect(Collectors.toList())));
    }

    @Test
    void ignoreMessagesForBlacklistedChannels() {

        String channel1Name = "channel 1 name";
        String channel2Name = "channel 2 name";
        when(config.getBlacklistedChannels()).thenReturn(Arrays.asList(channel1Name, channel2Name));
        Set<Channel> channels = channelsGenerator.next();
        channels.add(new Channel("whatever", channel1Name));
        channels.add(new Channel("whatever2", channel2Name));
        when(slackPort.getChannelList(logger)).thenReturn(channels);

        WorkspaceMessages messages = testee.getMessagesOnDate(LocalDate.now(), logger);

        assertThat(messages.getChannelNames()).doesNotContain(channel1Name, channel2Name);
    }

}