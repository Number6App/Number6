package dev.number6.sentiment.dagger;

import dev.number6.comprehend.results.PresentableSentimentResults;
import dev.number6.message.ChannelMessagesComprehensionHandler;
import dev.number6.message.ChannelMessagesHandler;
import dev.number6.message.ChannelMessagesToComprehensionResultsFunction;
import dev.number6.message.ComprehensionResultsConsumer;
import dagger.Module;
import dagger.Provides;

@Module
public class ChannelMessagesHandlerModule {

    @Provides
    public ChannelMessagesHandler providesMessageComprehension(ChannelMessagesToComprehensionResultsFunction<PresentableSentimentResults> channelMessagesToComprehensionResultsFunction,
                                                               ComprehensionResultsConsumer<PresentableSentimentResults> comprehensionResultsConsumer) {
        return new ChannelMessagesComprehensionHandler<>(channelMessagesToComprehensionResultsFunction, comprehensionResultsConsumer);
    }
}
