package dev.number6.slackreader.adaptor

import com.amazonaws.services.sns.AmazonSNS
import com.amazonaws.services.sns.model.PublishRequest
import com.google.gson.Gson
import dev.number6.message.ChannelMessages
import dev.number6.slackreader.port.NotificationPort
import dev.number6.slackreader.port.SlackReaderConfigurationPort

class SnsNotificationAdaptor(config: SlackReaderConfigurationPort, private val sns: AmazonSNS) : NotificationPort {
    private val gson: Gson = Gson()
    private val topicArn: String = config.getTopicArn()

    override fun broadcast(messages: ChannelMessages) {
        sns.publish(PublishRequest(topicArn, gson.toJson(messages)))
    }

}