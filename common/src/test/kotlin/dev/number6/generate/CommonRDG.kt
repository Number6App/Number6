package dev.number6.generate

import uk.org.fyodor.generators.RDG

object CommonRDG : RDG() {
    private val channelComprehensionSummaryGenerator = ChannelComprehensionSummaryGenerator()
    fun channelComprehensionSummary(): ChannelComprehensionSummaryGenerator {
        return channelComprehensionSummaryGenerator
    }
}