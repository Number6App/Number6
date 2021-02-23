package dev.number6.entity

import dev.number6.entity.dagger.DaggerChannelMessagesEntityComprehensionComponent
import dev.number6.message.ChannelMessagesNotificationRequestHandler

class ChannelMessagesEntityRequestHandler :
        ChannelMessagesNotificationRequestHandler(DaggerChannelMessagesEntityComprehensionComponent.create().getChannelMessagesHandler())