package dev.number6.slackposter.adaptor;

import dev.number6.slack.adaptor.SlackClientAdaptor;
import dev.number6.slack.port.HttpPort;
import dev.number6.slackposter.model.Chat;
import dev.number6.slackposter.model.PresentableChannelSummary;
import dev.number6.slackposter.port.SlackPort;
import dev.number6.slackposter.port.SlackPosterConfigurationPort;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.Optional;

public class SlackPosterAdaptor extends SlackClientAdaptor implements SlackPort {

    private final Gson gson = new Gson();
    private final SlackPosterConfigurationPort config;

    public SlackPosterAdaptor(HttpPort client,
                              SlackPosterConfigurationPort config) {
        super(client);
        this.config = config;
    }

    public void postMessageToChannel(PresentableChannelSummary summary, LambdaLogger logger) {

        logger.log("creating content for post to Slack: " + summary.getInitialMessageLine() + ", " + Arrays.toString(summary.getAttachments()));
        String content = gson.toJson(new Chat(config.getPostingChannelId(), summary.getInitialMessageLine(), summary.getAttachments()));
        logger.log("posting to Slack...");

        Optional<String> response = getSlackResponse(config.getSlackPostMessageUrl(),
                logger,
                content);
        logger.log("Response from post to Slack: " + response.orElse("no response"));
    }

}
