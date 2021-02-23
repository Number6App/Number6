package dev.number6.slackreader.adaptor

import dev.number6.slackreader.port.SlackReaderConfigurationPort

class EnvironmentVariableSlackReaderConfigurationAdapter : SlackReaderConfigurationPort {
    private val topicArn = System.getenv(SLACK_MSG_TOPIC_ARN)
    private val blackListedChannels = System.getenv(BLACKLISTED_CHANNELS)
    override fun getTopicArn(): String {
        return topicArn
    }

    override fun getBlacklistedChannels(): Collection<String> {
        return if (blackListedChannels == null) listOf() else listOf(*blackListedChannels.split(",".toRegex()).toTypedArray())
    }

    companion object {
        private val SLACK_MSG_TOPIC_ARN: String? = "SLACK_MSG_TOPIC_ARN"
        private val BLACKLISTED_CHANNELS: String? = "BLACKLISTED_CHANNELS"
    }
}