package dev.number6.entity.dagger

import dagger.Module
import dagger.Provides
import dev.number6.comprehend.results.PresentableEntityResults
import dev.number6.message.ChannelMessagesComprehensionHandler
import dev.number6.message.ChannelMessagesHandler
import dev.number6.message.ChannelMessagesToComprehensionResultsFunction
import dev.number6.message.ComprehensionResultsConsumer

@Module
class ChannelMessagesHandlerModule {
    @Provides
    fun providesMessageComprehension(channelMessagesToComprehensionResultsFunction: ChannelMessagesToComprehensionResultsFunction<PresentableEntityResults>,
                                     comprehensionResultsConsumer: ComprehensionResultsConsumer<PresentableEntityResults>): ChannelMessagesHandler {
        return ChannelMessagesComprehensionHandler<PresentableEntityResults>(channelMessagesToComprehensionResultsFunction, comprehensionResultsConsumer)
    }
}