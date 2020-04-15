package dev.number6.slackposter

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent.DynamodbStreamRecord
import dev.number6.slackposter.dagger.DaggerSlackPosterComponent
import java.util.function.Consumer

class SlackPosterRequestHandler : RequestHandler<DynamodbEvent, String> {
    private val slackService = DaggerSlackPosterComponent.create().handler()
    override fun handleRequest(o: DynamodbEvent, context: Context): String {
        context.logger.log("processing " + o.records.size + " records on event")
        context.logger.log("processing " + o.records)
        try {
            o.records.forEach(Consumer { r: DynamodbStreamRecord ->
                if (!r.eventName.equals("REMOVE", ignoreCase = true)) {
                    val vals = r.dynamodb.newImage
                    slackService.handleNewImage(ChannelSummaryImage(vals), context.logger)
                }
            })
        } catch (e: Exception) {
            context.logger.log("exception caught posting to Slack: " + e.message)
        }
        return "ok"
    }
}