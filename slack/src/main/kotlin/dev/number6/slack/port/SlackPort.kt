package dev.number6.slack.port

import com.amazonaws.services.lambda.runtime.LambdaLogger
import dev.number6.slack.model.Channel
import dev.number6.slack.model.ChannelHistoryResponse
import dev.number6.slack.model.ChannelsListResponse
import dev.number6.slack.model.JoinChannelResponse
import java.time.LocalDate
import java.util.*

interface SlackPort {
    fun postToSlackNoResponse(url: String, logger: LambdaLogger, body: String): Optional<String>
    fun <T> getSlackResponse(url: String, responseType: Class<T>?, logger: LambdaLogger): Optional<T>
    fun <T> callSlack(url: String, body: String?, responseType: Class<T>?, logger: LambdaLogger): Optional<T>
    fun postMessageToChannel(content: String, logger: LambdaLogger)
    fun getChannelList(logger: LambdaLogger): ChannelsListResponse
    fun getMessagesForChannelOnDate(c: Channel, date: LocalDate, logger: LambdaLogger): ChannelHistoryResponse
    fun joinChannel(c: Channel, logger: LambdaLogger): JoinChannelResponse
}