package dev.number6.slack.generate

import dev.number6.slack.model.Channel
import dev.number6.slack.model.ChannelHistoryResponse
import dev.number6.slack.model.Message
import uk.org.fyodor.generators.Generator
import uk.org.fyodor.generators.RDG
import uk.org.fyodor.range.Range

object SlackRDG : RDG() {
    private val channelGenerator = ChannelGenerator()
    private val channelsListResponseGenerator = ChannelsListResponseGenerator()

    fun channels(size: Range<Int>): Generator<MutableSet<Channel>> {
        return set(channel(), size)
    }

    fun channelsListResponse(): ChannelsListResponseGenerator {
        return channelsListResponseGenerator
    }

    fun channel(): Generator<Channel> {
        return channelGenerator
    }

    fun message(): Generator<Message> {
        return MessageGenerator()
    }

    fun channelHistoryResponse(success: Boolean? = null): Generator<ChannelHistoryResponse> {
        return ChannelHistoryGenerator(success)
    }
}