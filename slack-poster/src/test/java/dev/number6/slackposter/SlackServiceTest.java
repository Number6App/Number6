package dev.number6.slackposter;

import dev.number6.slackposter.port.SlackPort;
import dev.number6.slackposter.model.ChannelSummaryImageBuilder;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SlackServiceTest {

    @Mock
    LambdaLogger logger;
    @Mock
    SlackPort slackPort;
    @InjectMocks
    SlackService testee;

    @Test
    void postToSlackIfLastUpdate() throws IOException {

        ChannelSummaryImage image = ChannelSummaryImageBuilder.finalImage().build();
        testee.handleNewImage(image, logger);
        verify(slackPort, times(1)).postMessageToChannel(any(), any());
    }

    @Test
    void doNotPostToSlackIfNotLastUpdate() {

        ChannelSummaryImage image = ChannelSummaryImageBuilder.notFinalImage().build();
        testee.handleNewImage(image, logger);
        verifyZeroInteractions(slackPort);
    }


}