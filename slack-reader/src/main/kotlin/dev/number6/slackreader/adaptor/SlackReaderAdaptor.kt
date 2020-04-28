package dev.number6.slackreader.adaptor

import com.amazonaws.services.lambda.runtime.LambdaLogger
import dev.number6.slack.adaptor.SlackClientAdaptor.Companion.CHANNEL_HISTORY_URL
import dev.number6.slack.adaptor.SlackClientAdaptor.Companion.CHANNEL_LIST_URL
import dev.number6.slack.adaptor.SlackClientAdaptor.Companion.JOIN_CHANNEL_URL
import dev.number6.slack.port.SlackPort
import dev.number6.slackreader.model.*
import dev.number6.slackreader.port.SlackReaderPort
import java.time.LocalDate
import java.time.ZoneOffset

open class SlackReaderAdaptor(private val client: SlackPort) : SlackReaderPort {
    override fun getChannelList(logger: LambdaLogger): Collection<Channel> {
        val response = client.getSlackResponse(CHANNEL_LIST_URL,
                ChannelsListResponse::class.java,
                logger)
        return if (response.isPresent) response.get().channels else listOf()
    }

    override fun getMessagesForChannelOnDate(c: Channel, date: LocalDate, logger: LambdaLogger): Collection<Message> {
        val join = client.callSlack(String.format(JOIN_CHANNEL_URL, c.id), "", JoinChannelResponse::class.java, logger)
        return if (join.orElse(JoinChannelResponse.failed()).ok) {
            val response = client.getSlackResponse(String.format(CHANNEL_HISTORY_URL,
                    c.id,
                    date.atStartOfDay().toEpochSecond(ZoneOffset.UTC),
                    date.plusDays(1).atStartOfDay().toEpochSecond(ZoneOffset.UTC)),
                    ChannelHistoryResponse::class.java,
                    logger)
            response.map { chr ->
                chr.messages.filter { m -> m.text != null && m.text.isNotEmpty() }
                        .filter { m -> !m.isBotMessage() }
            }
                    .orElseGet { listOf() }
        } else {
            logger.log("Error trying to join channel $c")
            listOf()
        }
    }
}