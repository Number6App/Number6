package dev.number6.message

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.amazonaws.services.lambda.runtime.events.SNSEvent
import com.google.gson.Gson

open class ChannelMessagesNotificationRequestHandler(private val channelMessagesHandler: ChannelMessagesHandler) : RequestHandler<SNSEvent, String> {
    private val gson = Gson()
    override fun handleRequest(event: SNSEvent, context: Context): String {
        val logger = context.logger
        logger.log("Starting SnsMessage Entity Comprehension.")
        logger.log("Received event: $event")
        logger.log("containing " + event.records.size + " records.")
        val channelMessages = gson.fromJson(event.records[0].sns.message, ChannelMessages::class.java)
        channelMessagesHandler.handle(channelMessages)
        return "ok"
    }

}