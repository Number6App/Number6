package dev.number6.slackposter

import com.amazonaws.services.lambda.runtime.LambdaLogger
import dev.number6.slackposter.model.PresentableChannelSummary
import dev.number6.slackposter.port.SlackPort
import javax.inject.Inject

class SlackService @Inject constructor(private val slackPort: SlackPort) {

    fun handleNewImage(image: ChannelSummaryImage, logger: LambdaLogger) {
        logger.log("handling image: $image")
        if (image.hasFinalUpdate()) {
            slackPort.postMessageToChannel(PresentableChannelSummary(image), logger)
        }
    }
}