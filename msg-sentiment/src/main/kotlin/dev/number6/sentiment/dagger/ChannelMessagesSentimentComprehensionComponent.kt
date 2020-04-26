package dev.number6.sentiment.dagger

import dagger.Component
import dev.number6.CommonModule
import dev.number6.message.ChannelMessagesHandler

@Component(modules = [CommonModule::class, ChannelMessagesHandlerModule::class, ComprehensionResultsModule::class])
interface ChannelMessagesSentimentComprehensionComponent {
    fun getChannelMessagesHandler(): ChannelMessagesHandler
}