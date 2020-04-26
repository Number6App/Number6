package dev.number6.slackreader.dagger

import dagger.Module
import dagger.Provides
import dev.number6.slack.port.HttpPort
import dev.number6.slackreader.port.SlackPort
import javax.inject.Singleton

@Module
class RecordingSlackPortModule {
    @Provides
    @Singleton
    fun providesSlackPort(client: HttpPort): SlackPort {
        return RecordingSlackReaderAdaptor(client)
    }
}