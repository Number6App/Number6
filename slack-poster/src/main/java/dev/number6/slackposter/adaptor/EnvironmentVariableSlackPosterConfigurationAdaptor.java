package dev.number6.slackposter.adaptor;

import dev.number6.slackposter.port.SlackPosterConfigurationPort;

public class EnvironmentVariableSlackPosterConfigurationAdaptor implements SlackPosterConfigurationPort {

    public static final String META_CHANNEL_ID = "META_CHANNEL_ID";
    public static final String SLACK_POST_MESSAGE_URL = "SLACK_POST_MESSAGE_URL";

    @Override
    public String getPostingChannelId() {
        return System.getenv(META_CHANNEL_ID);
    }

    @Override
    public String getSlackPostMessageUrl() {
        return System.getenv(SLACK_POST_MESSAGE_URL);
    }
}
