package dev.number6.slack.dagger

import dagger.Module
import dagger.Provides
import dev.number6.slack.adaptor.SlackClientAdaptor
import dev.number6.slack.port.HttpPort
import dev.number6.slack.port.SlackPort

@Module
class SlackClientModule {
    @Provides
    fun providesSlackPort(client: HttpPort): SlackPort {
        return SlackClientAdaptor(client)
    }
}