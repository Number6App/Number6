package dev.number6.slackreader.port

interface SlackReaderConfigurationPort {
    fun getTopicArn(): String
    fun getBlacklistedChannels(): Collection<String>
}