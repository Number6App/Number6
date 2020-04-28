package dev.number6.slackreader.dagger

import dagger.Module
import dagger.Provides
import dev.number6.slack.adaptor.SlackClientAdaptor
import dev.number6.slackreader.adaptor.SlackReaderAdaptor
import dev.number6.slackreader.port.SlackReaderPort

@Module
class SlackReaderPortModule {
    @Provides
    fun slackClient(client: SlackClientAdaptor): SlackReaderPort {
        return SlackReaderAdaptor(client)
    }
}