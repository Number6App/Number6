package dev.number6.entity;

import dev.number6.comprehend.results.PresentableEntityResults;
import dev.number6.entity.dagger.DaggerTestChannelMessagesEntityComprehensionComponent;
import dev.number6.entity.dagger.FakeComprehensionResultsModule;
import dev.number6.entity.dagger.TestChannelMessagesEntityComprehensionComponent;
import dev.number6.message.ChannelMessages;
import dev.number6.message.ChannelMessagesHandler;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ChannelMessagesEntityComprehensionComponentIntegrationTest {

    TestChannelMessagesEntityComprehensionComponent testee = DaggerTestChannelMessagesEntityComprehensionComponent.create();

    @Test
    void providesChannelHandler() {

        PresentableEntityResults results = mock(PresentableEntityResults.class);
        ChannelMessagesHandler handler = testee.getChannelMessagesHandler();
        FakeComprehensionResultsModule.ConfigurableResultsFunction f = (FakeComprehensionResultsModule.ConfigurableResultsFunction) testee.getResultsFunction();
        f.setPresentableEntityResults(results);

        handler.handle(mock(ChannelMessages.class));

        FakeComprehensionResultsModule.RecordingComprehensionResultsConsumer consumer = (FakeComprehensionResultsModule.RecordingComprehensionResultsConsumer) testee.getConsumer();
        assertThat(consumer.getResultsConsumed()).hasSize(1);
        assertThat(consumer.getResultsConsumed().get(0)).isEqualTo(results);
    }
}