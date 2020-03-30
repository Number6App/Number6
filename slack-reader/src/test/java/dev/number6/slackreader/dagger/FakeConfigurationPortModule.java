package dev.number6.slackreader.dagger;

import dev.number6.slackreader.adaptor.EnvironmentVariableSlackReaderConfigurationAdapter;
import dev.number6.slackreader.port.SlackReaderConfigurationPort;
import dagger.Module;
import dagger.Provides;

@Module
public class FakeConfigurationPortModule {

    @Provides
    public SlackReaderConfigurationPort slackReaderConfigurationPort() {
        return new EnvironmentVariableSlackReaderConfigurationAdapter();
    }
}
