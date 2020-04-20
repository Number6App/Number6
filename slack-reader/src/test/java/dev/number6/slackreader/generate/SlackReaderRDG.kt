package dev.number6.slackreader.generate

import com.google.gson.Gson
import dev.number6.slackreader.model.Channel
import dev.number6.slackreader.model.ChannelHistoryResponse
import dev.number6.slackreader.model.Message
import dev.number6.slackreader.model.WorkspaceMessages
import uk.org.fyodor.generators.Generator
import uk.org.fyodor.generators.RDG
import uk.org.fyodor.range.Range
import java.time.LocalDate

object SlackReaderRDG : RDG() {
    private val channelGenerator = ChannelGenerator()
    private val channelsListResponseGenerator = ChannelsListResponseGenerator()
    private val gson = Gson()
    fun workspaceMessages(numberOfActiveChannels: Range<Int>): Generator<WorkspaceMessages> {
        return WorkspaceMessagesGenerator(LocalDate.now(), numberOfActiveChannels)
    }

    fun channels(size: Range<Int>): Generator<MutableSet<Channel>> {
        return set(channel(), size)
    }

    fun channelsListResponse(): ChannelsListResponseGenerator {
        return channelsListResponseGenerator
    }

    fun jsonChannelsListResponse(): Generator<String> {
        return Generator { gson.toJson(channelsListResponse().next()) }
    }

    fun channel(): Generator<Channel> {
        return channelGenerator
    }

    fun message(): Generator<Message> {
        return MessageGenerator()
    }

    fun jsonChannelHistoryResponse(): Generator<String> {
        return Generator { gson.toJson(channelHistoryResponse().next()) }
    }

    fun channelHistoryResponse(): Generator<ChannelHistoryResponse> {
        return ChannelHistoryGenerator()
    }
}