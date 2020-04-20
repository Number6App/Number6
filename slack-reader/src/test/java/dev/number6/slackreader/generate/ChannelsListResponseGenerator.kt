package dev.number6.slackreader.generate

import dev.number6.slackreader.model.ChannelsListResponse
import uk.org.fyodor.generators.Generator
import uk.org.fyodor.range.Range
import java.lang.Boolean

class ChannelsListResponseGenerator : Generator<ChannelsListResponse> {
    override fun next(): ChannelsListResponse {
        return ChannelsListResponse(Boolean.TRUE, SlackReaderRDG.channels(Range.closed(5, 20)).next())
    }
}