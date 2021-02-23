package dev.number6.entity.dagger

import dagger.Component
import dev.number6.comprehend.results.PresentableEntityResults
import dev.number6.message.ChannelMessagesToComprehensionResultsFunction
import dev.number6.message.ComprehensionResultsConsumer
import javax.inject.Singleton

@Component(modules = [FakeComprehendModule::class, ChannelMessagesHandlerModule::class, FakeComprehensionResultsModule::class])
@Singleton
interface TestChannelMessagesEntityComprehensionComponent : ChannelMessagesEntityComprehensionComponent {
    @Singleton
    fun getResultsFunction(): ChannelMessagesToComprehensionResultsFunction<PresentableEntityResults>

    @Singleton
    fun getConsumer(): ComprehensionResultsConsumer<PresentableEntityResults>
}