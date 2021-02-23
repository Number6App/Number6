package dev.number6.slackreader.generate

import dev.number6.slackreader.model.WorkspaceMessages
import uk.org.fyodor.generators.Generator
import uk.org.fyodor.generators.RDG
import uk.org.fyodor.range.Range
import java.time.LocalDate

internal class WorkspaceMessagesGenerator(private val date: LocalDate, private val numberOfActiveChannels: Range<Int>) : Generator<WorkspaceMessages> {
    override fun next(): WorkspaceMessages? {
        val messages = WorkspaceMessages(date)
        for (i in 0 until RDG.integer(numberOfActiveChannels).next()) {
            messages.add(RDG.string().next(), RDG.list(RDG.string(Range.closed(1, 50))).next())
        }
        return messages
    }

}