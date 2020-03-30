package dev.number6.keyphrases.dagger;

import dev.number6.comprehend.results.PresentableKeyPhrasesResults;
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
public interface TestChannelMessagesKeyPhrasesComprehensionComponent extends ChannelMessagesKeyPhrasesComprehensionComponent {

    @Singleton
    ChannelMessagesToComprehensionResultsFunction<PresentableKeyPhrasesResults> getResultsFunction();

    @Singleton
    ComprehensionResultsConsumer<PresentableKeyPhrasesResults> getConsumer();
}
