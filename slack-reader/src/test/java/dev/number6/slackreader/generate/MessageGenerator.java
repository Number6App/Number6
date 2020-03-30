package dev.number6.slackreader.generate;

import dev.number6.slackreader.model.Message;
import uk.org.fyodor.generators.Generator;
import uk.org.fyodor.range.Range;

class MessageGenerator implements Generator<Message> {

    @Override
    public Message next() {
        return new Message("message", SlackReaderRDG.string(Range.closed(10, 500)).next());
    }
}
