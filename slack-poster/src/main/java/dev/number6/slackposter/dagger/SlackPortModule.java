package dev.number6.slackposter.dagger;

import dev.number6.slack.port.HttpPort;
import dev.number6.slackposter.adaptor.SlackPosterAdaptor;
import dev.number6.slackposter.port.SlackPort;
import dev.number6.slackposter.port.SlackPosterConfigurationPort;
import dagger.Module;
import dagger.Provides;

@Module
public class SlackPortModule {

    @Provides
    public SlackPort providesSlackPort(HttpPort httpPort,
                                       SlackPosterConfigurationPort config) {
        return new SlackPosterAdaptor(httpPort, config);
    }
}
