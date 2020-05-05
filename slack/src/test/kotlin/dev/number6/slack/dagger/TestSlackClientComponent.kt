package dev.number6.slack.dagger

import dagger.Component
import dev.number6.slack.dagger.FakeAWSSecretsManagerModule
import dev.number6.slack.port.SlackPort
import javax.inject.Singleton

@Component(modules = [SlackClientModule::class,
    HttpModule::class,
    FakeAWSSecretsManagerModule::class,
    FakeHttpClientModule::class])
@Singleton
internal interface TestSlackClientComponent {
    fun slackPort(): SlackPort
}