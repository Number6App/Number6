package dev.number6.slackposter.dagger;

import dev.number6.slackposter.adaptor.EnvironmentVariableSlackPosterConfigurationAdaptor;
import dev.number6.slackposter.port.SlackPosterConfigurationPort;
import dagger.Module;
import dagger.Provides;

@Module
public class SlackPosterConfigurationModule {

    @Provides
    public SlackPosterConfigurationPort configPort() {
        return new EnvironmentVariableSlackPosterConfigurationAdaptor();
    }

}
