package dev.number6.slackreader.adaptor

import com.amazonaws.services.lambda.runtime.LambdaLogger
import dev.number6.slack.model.Channel
import dev.number6.slack.model.Message
import dev.number6.slack.port.SlackPort
import dev.number6.slackreader.port.SlackReaderPort
import java.time.LocalDate

open class SlackReaderAdaptor(private val client: SlackPort) : SlackReaderPort {
    override fun getChannelList(logger: LambdaLogger): Collection<Channel> {
        val response = client.getChannelList(logger)
        return if (response.ok) {
            response.channels
        } else {
            logger.log("Error trying to get channel list")
            listOf()
        }
    }

    override fun getMessagesForChannelOnDate(c: Channel, date: LocalDate, logger: LambdaLogger): Collection<Message> {
        val join = client.joinChannel(c, logger)
        return if (join.ok) {

            val response = client.getMessagesForChannelOnDate(c, date, logger)

            response.messages.filter { m -> !m.text.isNullOrEmpty() }
                    .filter { m -> !m.isBotMessage() }
        } else {
            logger.log("Error trying to join channel $c")
            listOf()
        }
    }
}