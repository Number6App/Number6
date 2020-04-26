package dev.number6.slackreader.port

import dev.number6.message.ChannelMessages

interface NotificationPort {
    open fun broadcast(messages: ChannelMessages)
}