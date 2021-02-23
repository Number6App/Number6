package dev.number6.slackreader.generate

import dev.number6.slack.model.Message
import uk.org.fyodor.generators.Generator
import uk.org.fyodor.generators.RDG
import uk.org.fyodor.range.Range

internal class MessageGenerator : Generator<Message> {
    override fun next(): Message {
        return Message("message", RDG.string(Range.closed(10, 500)).next())
    }
}