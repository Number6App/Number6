package dev.number6.slackreader.dagger

import dagger.Module
import dagger.Provides
import dev.number6.slack.port.HttpPort
import dev.number6.slackreader.adaptor.SlackReaderAdaptor

import dev.number6.slackreader.port.SlackPort

@Module
class SlackPortModule {
    @Provides
    fun slackClient(httpPort: HttpPort): SlackPort {
        return SlackReaderAdaptor(httpPort)
    }
}