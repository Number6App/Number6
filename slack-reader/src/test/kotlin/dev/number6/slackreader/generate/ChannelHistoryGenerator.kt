package dev.number6.slackreader.generate

import dev.number6.slack.model.ChannelHistoryResponse
import uk.org.fyodor.generators.Generator
import uk.org.fyodor.generators.RDG

internal class ChannelHistoryGenerator : Generator<ChannelHistoryResponse> {
    private val booleanGenerator = RDG.bool()
    private val timestampGenerator = RDG.longVal()
    private val messageListGenerator = RDG.list(SlackReaderRDG.message())
    override fun next(): ChannelHistoryResponse {
        return ChannelHistoryResponse(booleanGenerator.next(),
                messageListGenerator.next(),
                booleanGenerator.next(),
                timestampGenerator.next(),
                timestampGenerator.next())
    }
}