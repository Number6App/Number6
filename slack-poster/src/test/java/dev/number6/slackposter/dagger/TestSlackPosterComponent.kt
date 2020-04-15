package dev.number6.slackposter.dagger

import dagger.Component
import dev.number6.slack.port.HttpPort
import javax.inject.Singleton

@Component(modules = [SlackPortModule::class, FakeSlackPosterConfigurationModule::class, FakeHttpModule::class])
@Singleton
interface TestSlackPosterComponent : SlackPosterComponent {
    val recordingHttpAdaptor: HttpPort?
}