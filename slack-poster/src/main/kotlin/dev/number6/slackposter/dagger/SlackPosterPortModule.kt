package dev.number6.slackposter.dagger

import dagger.Module
import dagger.Provides
import dev.number6.slack.adaptor.SlackClientAdaptor
import dev.number6.slack.port.SlackPort
import dev.number6.slackposter.adaptor.SlackPosterAdaptor
import dev.number6.slackposter.port.SlackPosterPort
import dev.number6.slackposter.port.SlackPosterConfigurationPort

@Module
class SlackPosterPortModule {
    @Provides
    fun providesSlackPort(client: SlackPort,
                          config: SlackPosterConfigurationPort): SlackPosterPort {
        return SlackPosterAdaptor(client, config)
    }
}