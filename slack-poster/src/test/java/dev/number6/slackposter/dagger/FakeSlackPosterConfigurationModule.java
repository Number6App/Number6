package dev.number6.slackposter.dagger;

import dev.number6.slackposter.port.SlackPosterConfigurationPort;
import dagger.Module;
import dagger.Provides;

@Module
public class FakeSlackPosterConfigurationModule {

    public static final String SLACK_POST_MESSAGE_URL = "http://post.message.to/slack";
    public static final String POSTING_CHANNEL_ID = "POSTING_CHANNEL_ID";

    @Provides
    public SlackPosterConfigurationPort configPort() {
        return new SlackPosterConfigurationPort() {
            @Override
            public String getPostingChannelId() {
                return POSTING_CHANNEL_ID;
            }

            @Override
            public String getSlackPostMessageUrl() {
                return SLACK_POST_MESSAGE_URL;
            }
        };
    }

}
