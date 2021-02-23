package dev.number6.message

interface ChannelMessagesHandler {
    fun handle(channelMessages: ChannelMessages)
}