package dev.number6.slackposter.adaptor

import com.amazonaws.services.lambda.runtime.LambdaLogger
import com.google.gson.Gson
import dev.number6.slack.port.SlackPort
import dev.number6.slackposter.model.Chat
import dev.number6.slackposter.model.PresentableChannelSummary
import dev.number6.slackposter.port.SlackPosterConfigurationPort
import dev.number6.slackposter.port.SlackPosterPort

class SlackPosterAdaptor(private val client: SlackPort,
                         private val config: SlackPosterConfigurationPort) : SlackPosterPort {
    private val gson = Gson()
    override fun postMessageToChannel(summary: PresentableChannelSummary, logger: LambdaLogger) {
        logger.log("creating content for post to Slack: " + summary.initialMessageLine + ", " + summary.attachments.contentToString())
        val content = gson.toJson(Chat(config.postingChannelId, summary.initialMessageLine, *summary.attachments))
        logger.log("posting content to Slack: $content")
        client.postMessageToChannel(content, logger)
    }

}