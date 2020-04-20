package dev.number6.slackreader.dagger

import dagger.Module
import dagger.Provides
import dev.number6.slackreader.adaptor.EnvironmentVariableSlackReaderConfigurationAdapter

import dev.number6.slackreader.port.SlackReaderConfigurationPort

@Module
class ConfigurationPortModule {
    @Provides
    fun providesSlackReaderConfig(): SlackReaderConfigurationPort {
        return EnvironmentVariableSlackReaderConfigurationAdapter()
    }
}