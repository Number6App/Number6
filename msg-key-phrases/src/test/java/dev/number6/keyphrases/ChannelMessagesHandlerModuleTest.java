package dev.number6.keyphrases;

import dev.number6.comprehend.results.PresentableKeyPhrasesResults;
import dev.number6.keyphrases.dagger.ChannelMessagesHandlerModule;
import dev.number6.message.ChannelMessagesHandler;
import dev.number6.message.ChannelMessagesToComprehensionResultsFunction;
import dev.number6.message.ComprehensionResultsConsumer;
import dev.number6.message.ChannelMessages;
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
    PresentableKeyPhrasesResults results;
    @Mock
    ChannelMessagesToComprehensionResultsFunction<PresentableKeyPhrasesResults> toResults;
    @Mock
    ComprehensionResultsConsumer<PresentableKeyPhrasesResults> resultConsumer;

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