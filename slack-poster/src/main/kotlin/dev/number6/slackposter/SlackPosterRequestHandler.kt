package dev.number6.slackposter

import com.amazonaws.services.dynamodbv2.model.OperationType
import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent
import dev.number6.slackposter.dagger.DaggerSlackPosterComponent

fun DynamodbEvent.DynamodbStreamRecord.isModifyEvent(): Boolean {
    return OperationType.fromValue(this.eventName) == OperationType.MODIFY
}

class SlackPosterRequestHandler : RequestHandler<DynamodbEvent, String> {
    private val slackService = DaggerSlackPosterComponent.create().handler()

    override fun handleRequest(o: DynamodbEvent, context: Context): String {
        context.logger.log("processing " + o.records.size + " records on event")
        context.logger.log("processing " + o.records)
        try {
            o.records.forEach { r ->
                if (r.isModifyEvent()) {
                    val vals = r.dynamodb.newImage
                    slackService.handleNewImage(ChannelSummaryImage(vals), context.logger)
                }
            }
        } catch (e: Exception) {
            context.logger.log("exception caught posting to Slack: " + e.message)
        }
        return "ok"
    }
}