package dev.number6.slackposter.dagger

import dagger.Module
import dagger.Provides
import dev.number6.slack.dagger.SlackClientModule
import dev.number6.slack.port.SlackPort
import dev.number6.slackposter.adaptor.SlackPosterAdaptor
import dev.number6.slackposter.port.SlackPosterConfigurationPort
import dev.number6.slackposter.port.SlackPosterPort
import javax.inject.Singleton

@Module(includes = [SlackClientModule::class,
    FakeHttpClientModule::class])
class RecordingSlackPosterModule {

    @Provides
    @Singleton
    fun recordingSlackPoster(client: SlackPort,
                             config: SlackPosterConfigurationPort): SlackPosterPort {
        return RecordingSlackPosterAdaptor(SlackPosterAdaptor(client, config))
    }
}