package dev.number6.slackposter.dagger

import com.amazonaws.services.lambda.runtime.LambdaLogger
import dev.number6.slackposter.model.PresentableChannelSummary
import dev.number6.slackposter.port.SlackPosterPort

class RecordingSlackPosterAdaptor(private val poster: SlackPosterPort) : SlackPosterPort by poster {

    val posts: MutableList<PresentableChannelSummary> = mutableListOf()

    override fun postMessageToChannel(summary: PresentableChannelSummary, logger: LambdaLogger) {
        poster.postMessageToChannel(summary, logger)
        posts.add(summary)
    }
}