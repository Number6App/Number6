package dev.number6.slack

import dagger.Component
import dev.number6.slack.dagger.SlackClientModule
import dev.number6.slack.port.HttpPort
import dev.number6.slack.port.SlackPort
import javax.inject.Singleton

@Component(modules = [SlackClientModule::class, FakeHttpModule::class, FakeAWSSecretsManagerModule::class])
@Singleton
interface TestSlackClientComponent {
    fun slackPort(): SlackPort
    fun httpPort(): HttpPort
}