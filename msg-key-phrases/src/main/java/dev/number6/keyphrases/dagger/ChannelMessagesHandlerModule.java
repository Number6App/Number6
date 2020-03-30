package dev.number6.keyphrases.dagger;

import dev.number6.comprehend.results.PresentableKeyPhrasesResults;
import dev.number6.message.ChannelMessagesComprehensionHandler;
import dev.number6.message.ChannelMessagesHandler;
import dev.number6.message.ChannelMessagesToComprehensionResultsFunction;
import dev.number6.message.ComprehensionResultsConsumer;
import dagger.Module;
import dagger.Provides;

@Module
public class ChannelMessagesHandlerModule {

    @Provides
    public ChannelMessagesHandler providesMessageComprehension(ChannelMessagesToComprehensionResultsFunction<PresentableKeyPhrasesResults> channelMessagesToComprehensionResultsFunction,
                                                               ComprehensionResultsConsumer<PresentableKeyPhrasesResults> comprehensionResultsConsumer) {
        return new ChannelMessagesComprehensionHandler<>(channelMessagesToComprehensionResultsFunction, comprehensionResultsConsumer);
    }
}
