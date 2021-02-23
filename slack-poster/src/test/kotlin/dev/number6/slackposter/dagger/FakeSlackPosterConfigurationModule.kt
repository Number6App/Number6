package dev.number6.slackposter.dagger

import dagger.Module
import dagger.Provides
import dev.number6.slackposter.port.SlackPosterConfigurationPort

@Module
class FakeSlackPosterConfigurationModule {
    @Provides
    fun configPort(): SlackPosterConfigurationPort {
        return object : SlackPosterConfigurationPort {
            override val postingChannelId: String
                get() = POSTING_CHANNEL_ID
        }
    }

    companion object {
        const val POSTING_CHANNEL_ID = "POSTING_CHANNEL_ID"
    }
}