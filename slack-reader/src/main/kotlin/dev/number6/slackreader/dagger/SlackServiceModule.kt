package dev.number6.slackreader.dagger

import com.amazonaws.services.sns.AmazonSNS
import dagger.Module
import dagger.Provides
import dev.number6.slackreader.SlackService
import dev.number6.slackreader.SnsService
import dev.number6.slackreader.adaptor.SnsNotificationAdaptor
import dev.number6.slackreader.port.NotificationPort
import dev.number6.slackreader.port.SlackPort
import dev.number6.slackreader.port.SlackReaderConfigurationPort

@Module
class SlackServiceModule {
    @Provides
    fun snsService(notificationPort: NotificationPort): SnsService {
        return SnsService(notificationPort)
    }

    @Provides
    fun snsClient(config: SlackReaderConfigurationPort, aws: AmazonSNS): NotificationPort {
        return SnsNotificationAdaptor(config, aws)
    }

    @Provides
    fun slackService(slackPort: SlackPort, config: SlackReaderConfigurationPort): SlackService {
        return SlackService(slackPort, config)
    }
}