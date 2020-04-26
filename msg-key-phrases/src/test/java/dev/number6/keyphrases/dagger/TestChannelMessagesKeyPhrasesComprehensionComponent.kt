package dev.number6.keyphrases.dagger

import dagger.Component
import dev.number6.comprehend.results.PresentableKeyPhrasesResults
import dev.number6.message.ChannelMessagesToComprehensionResultsFunction
import dev.number6.message.ComprehensionResultsConsumer
import javax.inject.Singleton

@Component(modules = [FakeCommonModule::class, ChannelMessagesHandlerModule::class, FakeComprehensionResultsModule::class])
@Singleton
interface TestChannelMessagesKeyPhrasesComprehensionComponent : ChannelMessagesKeyPhrasesComprehensionComponent {
    @Singleton
    fun getResultsFunction(): ChannelMessagesToComprehensionResultsFunction<PresentableKeyPhrasesResults>

    @Singleton
    fun getConsumer(): ComprehensionResultsConsumer<PresentableKeyPhrasesResults>
}