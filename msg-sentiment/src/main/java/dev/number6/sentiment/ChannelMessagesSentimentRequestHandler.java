package dev.number6.sentiment;

import dev.number6.message.ChannelMessagesNotificationRequestHandler;
import dev.number6.sentiment.dagger.DaggerChannelMessagesSentimentComprehensionComponent;

public class ChannelMessagesSentimentRequestHandler extends ChannelMessagesNotificationRequestHandler {

    public ChannelMessagesSentimentRequestHandler() {
        super(DaggerChannelMessagesSentimentComprehensionComponent.create().getChannelMessagesHandler());
    }
}
