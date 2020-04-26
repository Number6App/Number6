package dev.number6.generate

import dev.number6.db.model.ChannelComprehensionSummary
import uk.org.fyodor.generators.Generator
import uk.org.fyodor.generators.RDG
import java.time.LocalDate

class ChannelComprehensionSummaryGenerator : Generator<ChannelComprehensionSummary> {
    override fun next(): ChannelComprehensionSummary {
        return ChannelComprehensionSummary(RDG.string().next(), LocalDate.now())
    }
}