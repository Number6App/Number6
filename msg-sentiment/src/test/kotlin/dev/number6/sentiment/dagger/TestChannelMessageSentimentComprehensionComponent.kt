package dev.number6.sentiment.dagger

import dagger.Component
import dev.number6.comprehend.results.PresentableSentimentResults
import dev.number6.message.ChannelMessagesToComprehensionResultsFunction
import dev.number6.message.ComprehensionResultsConsumer
import javax.inject.Singleton

@Component(modules = [FakeComprehendModule::class, ChannelMessagesHandlerModule::class, FakeComprehensionResultsModule::class])
@Singleton
interface TestChannelMessageSentimentComprehensionComponent : ChannelMessagesSentimentComprehensionComponent {
    @Singleton
    fun getResultsFunction(): ChannelMessagesToComprehensionResultsFunction<PresentableSentimentResults>

    @Singleton
    fun getConsumer(): ComprehensionResultsConsumer<PresentableSentimentResults>
}