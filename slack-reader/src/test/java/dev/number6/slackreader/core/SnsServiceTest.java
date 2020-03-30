package dev.number6.slackreader.core;

import dev.number6.slackreader.SnsService;
import dev.number6.slackreader.model.WorkspaceMessages;
import dev.number6.slackreader.port.NotificationPort;
import dev.number6.slackreader.generate.SlackReaderRDG;
import dev.number6.message.ChannelMessages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.org.fyodor.range.Range;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SnsServiceTest {

    @Mock
    private NotificationPort notificationPort;

    private SnsService testee;

    @BeforeEach
    void setup() {

        testee = new SnsService(notificationPort);
    }

    @Test
    void sendMessagesChannelToSns() {

        WorkspaceMessages workspaceMessages = new WorkspaceMessages(LocalDate.now());
        String channelName = SlackReaderRDG.string().next();
        List<String> messages = SlackReaderRDG.list(SlackReaderRDG.string(), Range.closed(10, 30)).next();

        workspaceMessages.add(channelName, messages);
        testee.broadcastWorkspaceMessagesForActiveChannels(workspaceMessages);

        ArgumentCaptor<ChannelMessages> captor = ArgumentCaptor.forClass(ChannelMessages.class);
        verify(notificationPort, times(1)).broadcast(captor.capture());

        assertThat(captor.getValue().getComprehensionDate()).isEqualTo(LocalDate.now());
        assertThat(captor.getValue().getChannelName()).isEqualTo(channelName);
        assertThat(captor.getValue().getMessages()).hasSameElementsAs(messages);
    }

    @Test
    void sendMessagesForActiveChannelToSns() {

        WorkspaceMessages workspaceMessages = SlackReaderRDG.workspaceMessages(Range.closed(5, 20)).next();

        String channelName = SlackReaderRDG.string().next();
        workspaceMessages.add(channelName, new ArrayList<>());

        testee.broadcastWorkspaceMessagesForActiveChannels(workspaceMessages);

        ArgumentCaptor<ChannelMessages> captor = ArgumentCaptor.forClass(ChannelMessages.class);
        verify(notificationPort, times(workspaceMessages.getActiveChannelNames().size())).broadcast(captor.capture());

        assertThat(captor.getAllValues()).hasSize(workspaceMessages.getActiveChannelNames().size());
        for (ChannelMessages channelMessages : captor.getAllValues()) {
            assertThat(channelMessages.getComprehensionDate()).isEqualTo(LocalDate.now());
            assertThat(channelMessages.getChannelName()).isIn(workspaceMessages.getActiveChannelNames());
            assertThat(channelMessages.getMessages()).containsExactlyInAnyOrderElementsOf(workspaceMessages.getMessagesForChannel(channelMessages.getChannelName()));
        }
    }
}