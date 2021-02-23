package dev.number6.slackposter.adaptor

import dev.number6.slackposter.port.SlackPosterConfigurationPort

class EnvironmentVariableSlackPosterConfigurationAdaptor : SlackPosterConfigurationPort {
    override val postingChannelId: String
        get() = System.getenv(META_CHANNEL_ID)

    companion object {
        const val META_CHANNEL_ID = "META_CHANNEL_ID"
    }
}