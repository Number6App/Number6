package dev.number6.slackposter.dagger

import dagger.Module
import dagger.Provides
import dev.number6.slackposter.adaptor.EnvironmentVariableSlackPosterConfigurationAdaptor
import dev.number6.slackposter.port.SlackPosterConfigurationPort

@Module
class SlackPosterConfigurationModule {
    @Provides
    fun configPort(): SlackPosterConfigurationPort {
        return EnvironmentVariableSlackPosterConfigurationAdaptor()
    }
}