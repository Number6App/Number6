package dev.number6.message;

import dev.number6.comprehend.results.PresentableEntityResults;
import dev.number6.generate.CommonRDG;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ChannelMessagesComprehensionHandlerTest {

    @Mock
    ChannelMessagesToComprehensionResultsFunction<PresentableEntityResults> resultsFunction;
    @Mock
    ComprehensionResultsConsumer<PresentableEntityResults> resultsConsumer;

    @Test
    void convertMessagesAndAppliesConsumer() {
        ChannelMessages channelMessages = CommonRDG.channelMessages().next();
        PresentableEntityResults results = CommonRDG.presentableEntityResults().next();
        when(resultsFunction.apply(any())).thenReturn(results);
        ChannelMessagesComprehensionHandler<PresentableEntityResults> testee =
                new ChannelMessagesComprehensionHandler<>(resultsFunction, resultsConsumer);

        testee.handle(channelMessages);
        verify(resultsFunction).apply(channelMessages);
        verify(resultsConsumer).accept(results);
    }
}