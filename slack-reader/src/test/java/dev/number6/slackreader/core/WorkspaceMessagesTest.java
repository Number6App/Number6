package dev.number6.slackreader.core;

import dev.number6.slackreader.model.WorkspaceMessages;
import dev.number6.slackreader.generate.SlackReaderRDG;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.org.fyodor.range.Range;

import java.util.ArrayList;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

class WorkspaceMessagesTest {

    private WorkspaceMessages testee;

    @BeforeEach
    void setup() {
        testee = new WorkspaceMessages();
    }

    @Test
    void activeChannelNamesReturnsChannelsWithMessages() {
        String inactiveChannelName = "inactive";

        testee.add(SlackReaderRDG.string(Range.closed(5, 30)).next(), SlackReaderRDG.list(SlackReaderRDG.string(), Range.closed(10, 20)).next());
        testee.add(inactiveChannelName, new ArrayList<>());
        testee.add(SlackReaderRDG.string(Range.closed(5, 30)).next(), SlackReaderRDG.list(SlackReaderRDG.string(), Range.closed(10, 20)).next());

        Collection<String> activeChannelNames = testee.getActiveChannelNames();

        assertThat(activeChannelNames).hasSize(2);
        assertThat(activeChannelNames).doesNotContain(inactiveChannelName);
    }

}