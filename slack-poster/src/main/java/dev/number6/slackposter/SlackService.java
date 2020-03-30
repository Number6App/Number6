package dev.number6.slackposter;

import dev.number6.slackposter.port.SlackPort;
import dev.number6.slackposter.model.PresentableChannelSummary;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

import javax.inject.Inject;

public class SlackService {

    private SlackPort slackPort;

    @Inject
    public SlackService(SlackPort slackPort) {
        this.slackPort = slackPort;
    }

    public void handleNewImage(ChannelSummaryImage image, LambdaLogger logger) {
        logger.log("handling image: " + image);

        if (image.hasFinalUpdate()) {
            slackPort.postMessageToChannel(new PresentableChannelSummary(image), logger);
        }
    }
}