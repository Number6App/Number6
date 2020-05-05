package dev.number6.slack.generate

import dev.number6.slack.model.ChannelHistoryResponse
import uk.org.fyodor.generators.Generator
import uk.org.fyodor.generators.RDG

internal class ChannelHistoryGenerator(private val success: Boolean?) : Generator<ChannelHistoryResponse> {
    private val booleanGenerator = RDG.bool()
    private val timestampGenerator = RDG.longVal()
    private val messageListGenerator = RDG.list(SlackRDG.message())
    override fun next(): ChannelHistoryResponse {
        return ChannelHistoryResponse(success ?: booleanGenerator.next(),
                messageListGenerator.next(),
                booleanGenerator.next(),
                timestampGenerator.next(),
                timestampGenerator.next())
    }
}