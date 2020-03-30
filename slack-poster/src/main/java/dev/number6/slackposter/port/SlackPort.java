package dev.number6.slackposter.port;

import dev.number6.slackposter.model.PresentableChannelSummary;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

public interface SlackPort {

    void postMessageToChannel(PresentableChannelSummary summary, LambdaLogger logger);
}
