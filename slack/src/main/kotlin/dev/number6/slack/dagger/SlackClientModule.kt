package dev.number6.slack.dagger

import dagger.Module
import dagger.Provides
import dev.number6.slack.adaptor.SlackClientAdaptor
import dev.number6.slack.port.SlackPort

@Module(includes = [HttpModule::class])
class SlackClientModule {
    @Provides
    fun providesPublicSlackClient(client: SlackClientAdaptor): SlackPort {
        return client
    }
}