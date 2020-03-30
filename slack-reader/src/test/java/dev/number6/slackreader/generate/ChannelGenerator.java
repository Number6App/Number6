package dev.number6.slackreader.generate;

import dev.number6.slackreader.model.Channel;
import uk.org.fyodor.generators.Generator;
import uk.org.fyodor.generators.characters.CharacterSetFilter;
import uk.org.fyodor.range.Range;

import static uk.org.fyodor.generators.RDG.string;

class ChannelGenerator implements Generator<Channel> {
    @Override
    public Channel next() {
        return new Channel(string(10, CharacterSetFilter.LettersAndDigits).next(), string(Range.closed(5, 25)).next());
    }
}
