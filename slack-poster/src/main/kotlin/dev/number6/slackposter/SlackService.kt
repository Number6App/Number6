package dev.number6.slackposter

import com.amazonaws.services.lambda.runtime.LambdaLogger
import dev.number6.slackposter.model.PresentableChannelSummary
import dev.number6.slackposter.port.SlackPosterPort
import javax.inject.Inject

class SlackService @Inject constructor(private val slackPosterPort: SlackPosterPort) {

    fun handleNewImage(image: ChannelSummaryImage, logger: LambdaLogger) {
        image.let {
            logger.log("handling image: $it")
            if (it.hasFinalUpdate()) {
                slackPosterPort.postMessageToChannel(PresentableChannelSummary(it), logger)
            }
        }
    }
}