package dev.number6.slackreader.port

interface SlackReaderConfigurationPort {
    open fun getTopicArn(): String
    open fun getBlacklistedChannels(): Collection<String>
}