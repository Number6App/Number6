package dev.number6.slackreader.generate;

import dev.number6.slackreader.model.ChannelsListResponse;
import uk.org.fyodor.generators.Generator;
import uk.org.fyodor.range.Range;

public class ChannelsListResponseGenerator implements Generator<ChannelsListResponse> {

    @Override
    public ChannelsListResponse next() {
        return new ChannelsListResponse(Boolean.TRUE, SlackReaderRDG.channels(Range.closed(5,20)).next());
    }
}
