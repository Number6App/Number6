package dev.number6.slackposter.dagger

import dagger.Module
import dagger.Provides
import dev.number6.slack.port.HttpPort
import dev.number6.slackposter.adaptor.SlackPosterAdaptor
import dev.number6.slackposter.port.SlackPort
import dev.number6.slackposter.port.SlackPosterConfigurationPort

@Module
class SlackPortModule {
    @Provides
    fun providesSlackPort(httpPort: HttpPort?,
                          config: SlackPosterConfigurationPort): SlackPort {
        return SlackPosterAdaptor(httpPort, config)
    }
}