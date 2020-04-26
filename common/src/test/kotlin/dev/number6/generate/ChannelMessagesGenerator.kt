package dev.number6.generate

import dev.number6.message.ChannelMessages
import uk.org.fyodor.generators.Generator
import uk.org.fyodor.generators.RDG
import java.time.LocalDate

class ChannelMessagesGenerator : Generator<ChannelMessages> {
    override fun next(): ChannelMessages {
        return ChannelMessages(RDG.string().next(), RDG.list(RDG.string()).next(), LocalDate.now())
    }
}