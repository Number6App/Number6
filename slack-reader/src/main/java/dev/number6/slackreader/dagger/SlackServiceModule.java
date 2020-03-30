package dev.number6.slackreader.dagger;

import dev.number6.slackreader.adaptor.SnsNotificationAdaptor;
import dev.number6.slackreader.SlackService;
import dev.number6.slackreader.SnsService;
import dev.number6.slackreader.port.NotificationPort;
import dev.number6.slackreader.port.SlackPort;
import dev.number6.slackreader.port.SlackReaderConfigurationPort;
import com.amazonaws.services.sns.AmazonSNS;
import dagger.Module;
import dagger.Provides;

@Module
public class SlackServiceModule {

    @Provides
    public SnsService snsService(NotificationPort notificationPort) {
        return new SnsService(notificationPort);
    }

    @Provides
    public NotificationPort snsClient(SlackReaderConfigurationPort config, AmazonSNS aws) {
        return new SnsNotificationAdaptor(config, aws);
    }

    @Provides
    public SlackService slackService(SlackPort slackPort, SlackReaderConfigurationPort config) {
        return new SlackService(slackPort, config);
    }
}
