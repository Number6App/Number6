package dev.number6.slackreader.port

import com.amazonaws.services.lambda.runtime.LambdaLogger

import dev.number6.slackreader.model.Channel
import dev.number6.slackreader.model.Message
import java.time.LocalDate

interface SlackPort {
    fun getChannelList(logger: LambdaLogger): Collection<Channel>
    fun getMessagesForChannelOnDate(c: Channel, date: LocalDate, logger: LambdaLogger): Collection<Message>
}