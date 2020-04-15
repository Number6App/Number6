package dev.number6.slackposter.adaptor

import com.amazonaws.services.lambda.runtime.LambdaLogger
import com.google.gson.Gson
import dev.number6.slack.adaptor.SlackClientAdaptor
import dev.number6.slack.port.HttpPort
import dev.number6.slackposter.model.Chat
import dev.number6.slackposter.model.PresentableChannelSummary
import dev.number6.slackposter.port.SlackPort
import dev.number6.slackposter.port.SlackPosterConfigurationPort
import java.util.*

class SlackPosterAdaptor(client: HttpPort?,
                         private val config: SlackPosterConfigurationPort) : SlackClientAdaptor(client), SlackPort {
    private val gson = Gson()
    override fun postMessageToChannel(summary: PresentableChannelSummary, logger: LambdaLogger) {
        logger.log("creating content for post to Slack: " + summary.initialMessageLine + ", " + Arrays.toString(summary.attachments))
        val content = gson.toJson(Chat(config.postingChannelId, summary.initialMessageLine, *summary.attachments))
        logger.log("posting to Slack...")
        val response = getSlackResponse(config.slackPostMessageUrl,
                logger,
                content)
        logger.log("Response from post to Slack: " + response.orElse("no response"))
    }

}