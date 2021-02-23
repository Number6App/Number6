package dev.number6.entity.dagger

import dagger.Component
import dev.number6.ComprehendModule
import dev.number6.message.ChannelMessagesHandler

@Component(modules = [ComprehendModule::class, ChannelMessagesHandlerModule::class, ComprehensionResultsModule::class])
interface ChannelMessagesEntityComprehensionComponent {
    fun getChannelMessagesHandler(): ChannelMessagesHandler
}