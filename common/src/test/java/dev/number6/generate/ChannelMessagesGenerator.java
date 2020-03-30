package dev.number6.generate;

import dev.number6.message.ChannelMessages;
import uk.org.fyodor.generators.Generator;

import java.time.LocalDate;

public class ChannelMessagesGenerator implements Generator<ChannelMessages> {

    @Override
    public ChannelMessages next() {
        return new ChannelMessages(CommonRDG.string().next(), CommonRDG.list(CommonRDG.string()).next(), LocalDate.now());
    }
}
