package dev.number6.slackposter.port;

public interface SlackPosterConfigurationPort {
    String getPostingChannelId();

    String getSlackPostMessageUrl();
}
