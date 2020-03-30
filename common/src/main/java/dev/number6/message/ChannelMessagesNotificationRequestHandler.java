package dev.number6.message;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import com.google.gson.Gson;

public class ChannelMessagesNotificationRequestHandler implements RequestHandler<SNSEvent, String> {

    private final Gson gson = new Gson();
    private final ChannelMessagesHandler channelMessagesHandler;

    public ChannelMessagesNotificationRequestHandler(ChannelMessagesHandler channelMessagesHandler) {
        this.channelMessagesHandler = channelMessagesHandler;
    }

    @Override
    public String handleRequest(SNSEvent event, Context context) {
        LambdaLogger logger = context.getLogger();

        logger.log("Starting SnsMessage Entity Comprehension.");
        logger.log("Received event: " + event);
        logger.log("containing " + event.getRecords().size() + " records.");

        ChannelMessages channelMessages = gson.fromJson(event.getRecords().get(0).getSNS().getMessage(), ChannelMessages.class);

        channelMessagesHandler.handle(channelMessages);
        return "ok";
    }
}
