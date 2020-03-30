package dev.number6.entity.dagger;

import dev.number6.comprehend.results.PresentableEntityResults;
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
public interface TestChannelMessagesEntityComprehensionComponent extends ChannelMessagesEntityComprehensionComponent {

    @Singleton
    ChannelMessagesToComprehensionResultsFunction<PresentableEntityResults> getResultsFunction();

    @Singleton
    ComprehensionResultsConsumer<PresentableEntityResults> getConsumer();
}
