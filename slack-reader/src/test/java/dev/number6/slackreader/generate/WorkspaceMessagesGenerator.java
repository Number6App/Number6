package dev.number6.slackreader.generate;

import dev.number6.slackreader.model.WorkspaceMessages;
import uk.org.fyodor.generators.Generator;
import uk.org.fyodor.range.Range;

import java.time.LocalDate;

class WorkspaceMessagesGenerator implements Generator<WorkspaceMessages> {

    private final LocalDate date;
    private final Range<Integer> numberOfActiveChannels;

    public WorkspaceMessagesGenerator(LocalDate date, Range<Integer> numberOfActiveChannels) {
        this.date = date;
        this.numberOfActiveChannels = numberOfActiveChannels;
    }

    @Override
    public WorkspaceMessages next() {
        WorkspaceMessages messages = new WorkspaceMessages(date);
        for (int i = 0; i < SlackReaderRDG.integer(numberOfActiveChannels).next(); i++) {
            messages.add(SlackReaderRDG.string().next(), SlackReaderRDG.list(SlackReaderRDG.string(Range.closed(1, 50))).next());
        }
        return messages;
    }
}
