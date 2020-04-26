package dev.number6.slackreader.generate

import dev.number6.slackreader.model.ChannelsListResponse
import uk.org.fyodor.generators.Generator
import uk.org.fyodor.range.Range

class ChannelsListResponseGenerator : Generator<ChannelsListResponse> {
    override fun next(): ChannelsListResponse {
        return ChannelsListResponse(true, SlackReaderRDG.channels(Range.closed(5, 20)).next())
    }
}