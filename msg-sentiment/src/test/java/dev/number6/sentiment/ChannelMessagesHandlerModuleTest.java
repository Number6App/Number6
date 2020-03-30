package dev.number6.sentiment;

import dev.number6.comprehend.results.PresentableSentimentResults;
import dev.number6.message.ChannelMessagesHandler;
import dev.number6.message.ChannelMessagesToComprehensionResultsFunction;
import dev.number6.message.ComprehensionResultsConsumer;
import dev.number6.message.ChannelMessages;
import dev.number6.sentiment.dagger.ChannelMessagesHandlerModule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChannelMessagesHandlerModuleTest {

    @Mock
    ChannelMessages channelMessages;
    @Mock
    PresentableSentimentResults results;
    @Mock
    ChannelMessagesToComprehensionResultsFunction<PresentableSentimentResults> toResults;
    @Mock
    ComprehensionResultsConsumer<PresentableSentimentResults> resultConsumer;

    ChannelMessagesHandlerModule module = new ChannelMessagesHandlerModule();

    @Test
    void providesChannelHandler() {

        ChannelMessagesHandler handler = module.providesMessageComprehension(toResults, resultConsumer);
        when(toResults.apply(channelMessages)).thenReturn(results);
        handler.handle(channelMessages);

        verify(toResults, times(1)).apply(channelMessages);
        verify(resultConsumer).accept(results);
    }

}