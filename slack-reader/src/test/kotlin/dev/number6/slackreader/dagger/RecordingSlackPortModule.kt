package dev.number6.slackreader.dagger

import dagger.Module
import dagger.Provides
import dev.number6.slack.adaptor.SlackClientAdaptor
import dev.number6.slackreader.port.SlackReaderPort
import javax.inject.Singleton

@Module
class RecordingSlackPortModule {
    @Provides
    @Singleton
    fun providesSlackPort(client: SlackClientAdaptor): SlackReaderPort {
        return RecordingSlackReaderAdaptor(client)
    }
}