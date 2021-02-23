package dev.number6.sentiment.dagger

import dagger.Module
import dagger.Provides
import dev.number6.comprehend.results.PresentableSentimentResults
import dev.number6.message.ChannelMessagesComprehensionHandler
import dev.number6.message.ChannelMessagesHandler
import dev.number6.message.ChannelMessagesToComprehensionResultsFunction
import dev.number6.message.ComprehensionResultsConsumer

@Module
class ChannelMessagesHandlerModule {
    @Provides
    fun providesMessageComprehension(channelMessagesToComprehensionResultsFunction: ChannelMessagesToComprehensionResultsFunction<PresentableSentimentResults>,
                                     comprehensionResultsConsumer: ComprehensionResultsConsumer<PresentableSentimentResults>): ChannelMessagesHandler {
        return ChannelMessagesComprehensionHandler(channelMessagesToComprehensionResultsFunction, comprehensionResultsConsumer)
    }
}