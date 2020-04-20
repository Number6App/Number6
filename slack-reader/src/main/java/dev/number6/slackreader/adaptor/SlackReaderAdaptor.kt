package dev.number6.slackreader.adaptor

import com.amazonaws.services.lambda.runtime.LambdaLogger
import dev.number6.slack.adaptor.SlackClientAdaptor
import dev.number6.slack.port.HttpPort
import dev.number6.slackreader.model.*
import dev.number6.slackreader.port.SlackPort
import java.time.LocalDate
import java.time.ZoneOffset

open class SlackReaderAdaptor(client: HttpPort) : SlackClientAdaptor(client), SlackPort {
    override fun getChannelList(logger: LambdaLogger): Collection<Channel> {
        val response = getSlackResponse(CHANNEL_LIST_URL,
                ChannelsListResponse::class.java,
                logger)
        return if (response.isPresent) response.get().getChannels() else listOf()
    }

    override fun getMessagesForChannelOnDate(c: Channel, date: LocalDate, logger: LambdaLogger): Collection<Message> {
        val join = getSlackResponse(String.format(JOIN_CHANNEL_URL, c.id), "", JoinChannelResponse::class.java, logger)
        return if (join.orElse(JoinChannelResponse.failed()).ok) {
            val response = getSlackResponse(String.format(CHANNEL_HISTORY_URL,
                    c.id,
                    date.atStartOfDay().toEpochSecond(ZoneOffset.UTC),
                    date.plusDays(1).atStartOfDay().toEpochSecond(ZoneOffset.UTC)),
                    ChannelHistoryResponse::class.java,
                    logger)
            response.map { chr ->
                chr.getMessages().filter { m -> m.text != null && m.text.isNotEmpty() }
                        .filter { m -> !m.isBotMessage() }
            }
                    .orElseGet { listOf() }
        } else {
            logger.log("Error trying to join channel $c")
            listOf()
        }
    }
}