package dev.number6.slack.generate

import dev.number6.slack.model.Channel
import uk.org.fyodor.generators.Generator
import uk.org.fyodor.generators.RDG
import uk.org.fyodor.generators.characters.CharacterSetFilter
import uk.org.fyodor.range.Range

internal class ChannelGenerator : Generator<Channel> {
    override fun next(): Channel {
        return Channel(RDG.string(10, CharacterSetFilter.LettersAndDigits).next(), RDG.string(Range.closed(5, 25)).next())
    }
}