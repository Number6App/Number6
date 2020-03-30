package dev.number6.sentiment.dagger;


import dev.number6.comprehend.results.PresentableSentimentResults;
import dev.number6.message.ChannelMessagesToComprehensionResultsFunction;
import dev.number6.message.ComprehensionResultsConsumer;
import dagger.Component;

import javax.inject.Singleton;

@Component(modules = {
        FakeCommonModule.class,
        ChannelMessagesHandlerModule.class,
        FakeComprehensionResultsModule.class
})
@Singleton
public interface TestChannelMessageSentimentComprehensionComponent extends ChannelMessagesSentimentComprehensionComponent {

    @Singleton
    ChannelMessagesToComprehensionResultsFunction<PresentableSentimentResults> getResultsFunction();

    @Singleton
    ComprehensionResultsConsumer<PresentableSentimentResults> getConsumer();

}
