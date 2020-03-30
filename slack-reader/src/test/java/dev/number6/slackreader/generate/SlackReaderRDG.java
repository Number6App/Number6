package dev.number6.slackreader.generate;

import dev.number6.slackreader.model.Channel;
import dev.number6.slackreader.model.ChannelHistoryResponse;
import dev.number6.slackreader.model.Message;
import dev.number6.slackreader.model.WorkspaceMessages;
import com.google.gson.Gson;
import uk.org.fyodor.generators.Generator;
import uk.org.fyodor.generators.RDG;
import uk.org.fyodor.range.Range;

import java.time.LocalDate;
import java.util.Set;

public class SlackReaderRDG extends RDG {

    private static final ChannelGenerator channelGenerator = new ChannelGenerator();
    private static final ChannelsListResponseGenerator channelsListResponseGenerator = new ChannelsListResponseGenerator();
    private static final Gson gson = new Gson();

    public static Generator<WorkspaceMessages> workspaceMessages(Range<Integer> numberOfActiveChannels) {
        return new WorkspaceMessagesGenerator(LocalDate.now(), numberOfActiveChannels);
    }

    public static Generator<Set<Channel>> channels(Range<Integer> size) {
        return set(channel(), size);
    }

    public static ChannelsListResponseGenerator channelsListResponse() {
        return channelsListResponseGenerator;
    }

    public static Generator<String> jsonChannelsListResponse() {
        return () -> gson.toJson(channelsListResponse().next());
    }

    public static Generator<Channel> channel() {
        return channelGenerator;
    }

    public static Generator<Message> message() {
        return new MessageGenerator();
    }

    public static Generator<String> jsonChannelHistoryResponse() {
        return () -> gson.toJson(channelHistoryResponse().next());
    }

    public static Generator<ChannelHistoryResponse> channelHistoryResponse() {
        return new ChannelHistoryGenerator();
    }
}
