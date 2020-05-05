package dev.number6.slackreader.dagger

import dagger.Module
import dagger.Provides
import dev.number6.slackreader.port.SlackReaderConfigurationPort

@Module
class FakeConfigurationPortModule {
    @Provides
    fun slackReaderConfigurationPort(): SlackReaderConfigurationPort {
        return object: SlackReaderConfigurationPort {
            override fun getTopicArn(): String {
                return "TOPIC_ARN"
            }

            override fun getBlacklistedChannels(): Collection<String> {
                return listOf("blacklisted", "channels")
            }

        }
    }
}