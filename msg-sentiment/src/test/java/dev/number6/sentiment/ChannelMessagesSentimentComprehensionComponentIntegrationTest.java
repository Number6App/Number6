package dev.number6.sentiment;

import dev.number6.comprehend.results.PresentableSentimentResults;
import dev.number6.message.ChannelMessages;
import dev.number6.message.ChannelMessagesHandler;
import dev.number6.sentiment.dagger.DaggerTestChannelMessageSentimentComprehensionComponent;
import dev.number6.sentiment.dagger.FakeComprehensionResultsModule;
import dev.number6.sentiment.dagger.TestChannelMessageSentimentComprehensionComponent;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class ChannelMessagesSentimentComprehensionComponentIntegrationTest {

    TestChannelMessageSentimentComprehensionComponent testee = DaggerTestChannelMessageSentimentComprehensionComponent.create();

    @Test
    void providesChannelHandler() {

        PresentableSentimentResults results = mock(PresentableSentimentResults.class);
        ChannelMessagesHandler handler = testee.getChannelMessagesHandler();
        FakeComprehensionResultsModule.ConfigurableResultsFunction f = (FakeComprehensionResultsModule.ConfigurableResultsFunction) testee.getResultsFunction();
        f.setPresentableSentimentResults(results);

        handler.handle(mock(ChannelMessages.class));

        FakeComprehensionResultsModule.RecordingComprehensionResultsConsumer consumer = (FakeComprehensionResultsModule.RecordingComprehensionResultsConsumer) testee.getConsumer();
        assertThat(consumer.getResultsConsumed()).hasSize(1);
        assertThat(consumer.getResultsConsumed().get(0)).isEqualTo(results);
    }
}