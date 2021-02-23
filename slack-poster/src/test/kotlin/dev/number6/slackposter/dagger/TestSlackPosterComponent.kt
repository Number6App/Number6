package dev.number6.slackposter.dagger

import dagger.Component
import dev.number6.slackposter.port.SlackPosterPort
import javax.inject.Singleton

@Component(modules = [FakeSlackPosterConfigurationModule::class,
    RecordingSlackPosterModule::class])
@Singleton
interface TestSlackPosterComponent : SlackPosterComponent {
    fun recordingHttpAdaptor(): SlackPosterPort
}