package dev.number6.keyphrases;

import dev.number6.comprehend.results.PresentableKeyPhrasesResults;
import dev.number6.keyphrases.dagger.DaggerTestChannelMessagesKeyPhrasesComprehensionComponent;
import dev.number6.keyphrases.dagger.FakeComprehensionResultsModule;
import dev.number6.keyphrases.dagger.TestChannelMessagesKeyPhrasesComprehensionComponent;
import dev.number6.message.ChannelMessages;
import dev.number6.message.ChannelMessagesHandler;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class ChannelMessagesKeyPhrasesComprehensionComponentIntegrationTest {

    TestChannelMessagesKeyPhrasesComprehensionComponent testee = DaggerTestChannelMessagesKeyPhrasesComprehensionComponent.create();

    @Test
    void providesChannelHandler() {

        PresentableKeyPhrasesResults results = mock(PresentableKeyPhrasesResults.class);
        ChannelMessagesHandler handler = testee.getChannelMessagesHandler();
        FakeComprehensionResultsModule.ConfigurableResultsFunction f = (FakeComprehensionResultsModule.ConfigurableResultsFunction) testee.getResultsFunction();
        f.setPresentableKeyPhrasesResults(results);

        handler.handle(mock(ChannelMessages.class));

        FakeComprehensionResultsModule.RecordingComprehensionResultsConsumer consumer = (FakeComprehensionResultsModule.RecordingComprehensionResultsConsumer) testee.getConsumer();
        assertThat(consumer.getResultsConsumed()).hasSize(1);
        assertThat(consumer.getResultsConsumed().get(0)).isEqualTo(results);
    }
}