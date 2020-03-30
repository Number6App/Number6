package dev.number6.slackposter;

import dev.number6.slackposter.dagger.DaggerSlackPosterComponent;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;

import java.util.Map;

public class SlackPosterRequestHandler implements RequestHandler<DynamodbEvent, String> {

    private SlackService slackService = DaggerSlackPosterComponent.create().handler();

    @Override
    public String handleRequest(DynamodbEvent o, Context context) {
        context.getLogger().log("processing " + o.getRecords().size() + " records on event");
        context.getLogger().log("processing " + o.getRecords());

        try {
            o.getRecords().forEach(r -> {
                if (!r.getEventName().equalsIgnoreCase("REMOVE")) {
                    Map<String, AttributeValue> vals = r.getDynamodb().getNewImage();
                    slackService.handleNewImage(new ChannelSummaryImage(vals), context.getLogger());

                }
            });
        } catch (Exception e) {
            context.getLogger().log("exception caught posting to Slack: " + e.getMessage());
        }
        return "ok";
    }
}
