package dev.number6.slack.adaptor

import com.amazonaws.services.lambda.runtime.LambdaLogger
import com.google.gson.Gson
import dev.number6.slack.model.Channel
import dev.number6.slack.model.ChannelHistoryResponse
import dev.number6.slack.model.ChannelsListResponse
import dev.number6.slack.model.JoinChannelResponse
import dev.number6.slack.port.HttpPort
import dev.number6.slack.port.SlackPort
import java.time.LocalDate
import java.time.ZoneOffset
import java.util.*
import javax.inject.Inject

class SlackClientAdaptor @Inject internal constructor(private val client: HttpPort) : SlackPort {
    private val gson = Gson()
    override fun postToSlackNoResponse(url: String, logger: LambdaLogger, body: String): Optional<String> {
        return callSlack(url, body, null, logger)
    }

    override fun <T> getSlackResponse(url: String, responseType: Class<T>?, logger: LambdaLogger): Optional<T> {
        return callSlack(url, null, responseType, logger)
    }

    override fun <T> callSlack(url: String, body: String?, responseType: Class<T>?, logger: LambdaLogger): Optional<T> {
        val response = if (body == null) client.get(url, logger) else client.post(url, body, logger)
        logger.log("Response from Slack request: ${response.body()}")
        return if (responseType == null || !response.isSuccess) Optional.empty() else Optional.of(gson.fromJson(response.body(), responseType))
    }

    override fun postMessageToChannel(content: String, logger: LambdaLogger) {
        postToSlackNoResponse(POST_MESSAGE_URL, logger, content)
    }

    override fun getChannelList(logger: LambdaLogger): ChannelsListResponse {
        return getSlackResponse(CHANNEL_LIST_URL, ChannelsListResponse::class.java, logger)
                .orElse(ChannelsListResponse(false, listOf()))
    }

    override fun getMessagesForChannelOnDate(c: Channel, date: LocalDate, logger: LambdaLogger): ChannelHistoryResponse {
//        TODO("Not yet implemented")
        return getSlackResponse(String.format(CHANNEL_HISTORY_URL,
                c.id,
                date.atStartOfDay().toEpochSecond(ZoneOffset.UTC),
                date.plusDays(1).atStartOfDay().toEpochSecond(ZoneOffset.UTC)),
                ChannelHistoryResponse::class.java,
                logger)
                .orElse(ChannelHistoryResponse(false, listOf(), false, date.toEpochDay(), date.toEpochDay()))
    }

    override fun joinChannel(c: Channel, logger: LambdaLogger): JoinChannelResponse {
        return callSlack(String.format(JOIN_CHANNEL_URL, c.id), "", JoinChannelResponse::class.java, logger)
                .orElse(JoinChannelResponse.failed())
    }

    companion object {
        const val CHANNEL_LIST_URL = "https://slack.com/api/channels.list"
        const val CHANNEL_HISTORY_URL = "https://slack.com/api/channels.history?count=1000&channel=%s&oldest=%s&latest=%s"
        const val JOIN_CHANNEL_URL = "https://slack.com/api/conversations.join?channel=%s"
        const val POST_MESSAGE_URL = "https://slack.com/api/chat.postMessage"
    }
}