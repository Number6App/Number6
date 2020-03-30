package dev.number6.keyphrases;

import dev.number6.keyphrases.dagger.DaggerChannelMessagesKeyPhrasesComprehensionComponent;
import dev.number6.message.ChannelMessagesNotificationRequestHandler;

public class ChannelMessagesKeyPhrasesRequestHandler extends ChannelMessagesNotificationRequestHandler {

    public ChannelMessagesKeyPhrasesRequestHandler() {
        super(DaggerChannelMessagesKeyPhrasesComprehensionComponent.create().getChannelMessagesHandler());
    }
}
