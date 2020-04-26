package dev.number6.keyphrases.dagger

import dagger.Module
import dagger.Provides
import dev.number6.comprehend.results.PresentableKeyPhrasesResults
import dev.number6.message.ChannelMessagesComprehensionHandler
import dev.number6.message.ChannelMessagesHandler
import dev.number6.message.ChannelMessagesToComprehensionResultsFunction
import dev.number6.message.ComprehensionResultsConsumer

@Module
class ChannelMessagesHandlerModule {
    @Provides
    fun providesMessageComprehension(channelMessagesToComprehensionResultsFunction: ChannelMessagesToComprehensionResultsFunction<PresentableKeyPhrasesResults>,
                                     comprehensionResultsConsumer: ComprehensionResultsConsumer<PresentableKeyPhrasesResults>): ChannelMessagesHandler {
        return ChannelMessagesComprehensionHandler<PresentableKeyPhrasesResults>(channelMessagesToComprehensionResultsFunction, comprehensionResultsConsumer)
    }
}