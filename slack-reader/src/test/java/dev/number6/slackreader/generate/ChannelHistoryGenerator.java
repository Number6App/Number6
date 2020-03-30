package dev.number6.slackreader.generate;


import dev.number6.slackreader.model.ChannelHistoryResponse;
import dev.number6.slackreader.model.Message;
import uk.org.fyodor.generators.Generator;

import java.util.List;

class ChannelHistoryGenerator implements Generator<ChannelHistoryResponse> {
    private final Generator<Boolean> booleanGenerator = SlackReaderRDG.bool();
    private final Generator<Long> timestampGenerator = SlackReaderRDG.longVal();
    private final Generator<List<Message>> messageListGenerator = SlackReaderRDG.list(SlackReaderRDG.message());

    @Override
    public ChannelHistoryResponse next() {

        return new ChannelHistoryResponse(booleanGenerator.next(),
                messageListGenerator.next(),
                booleanGenerator.next(),
                timestampGenerator.next(),
                timestampGenerator.next());
    }
}
