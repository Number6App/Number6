package dev.number6.entity;

import dev.number6.entity.dagger.DaggerChannelMessagesEntityComprehensionComponent;
import dev.number6.message.ChannelMessagesNotificationRequestHandler;

public class ChannelMessagesEntityRequestHandler extends ChannelMessagesNotificationRequestHandler {

    public ChannelMessagesEntityRequestHandler() {
        super(DaggerChannelMessagesEntityComprehensionComponent.create().getChannelMessagesHandler());
    }
}
