package dev.number6.slackreader.dagger

import com.amazonaws.services.lambda.runtime.LambdaLogger
import dev.number6.slack.adaptor.SlackClientAdaptor
import dev.number6.slackreader.adaptor.SlackReaderAdaptor
import dev.number6.slackreader.model.Channel
import dev.number6.slackreader.model.Message
import java.time.LocalDate

class RecordingSlackReaderAdaptor(client: SlackClientAdaptor) : SlackReaderAdaptor(client) {
    private var channelNames: List<String> = listOf()
    private val channelMessages: MutableMap<String?, List<String?>> = mutableMapOf()
    override fun getChannelList(logger: LambdaLogger): Collection<Channel> {
        val channels = super.getChannelList(logger)
        channelNames = channels.map { c -> c.name }
        return channels
    }

    override fun getMessagesForChannelOnDate(c: Channel, date: LocalDate, logger: LambdaLogger): Collection<Message> {
        val messages = super.getMessagesForChannelOnDate(c, date, logger)
        channelMessages[c.name] = messages.map { m -> m.text }
        return messages
    }

    fun getMessagesForChannelName(channelName: String): List<String?> {
        return channelMessages.getOrDefault(channelName, listOf())
    }

    fun getChannelNames(): List<String> {
        return channelNames
    }
}