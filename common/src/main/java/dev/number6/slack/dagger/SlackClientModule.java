package dev.number6.slack.dagger;

import dev.number6.slack.adaptor.SlackClientAdaptor;
import dev.number6.slack.port.HttpPort;
import dev.number6.slack.port.SlackPort;
import dagger.Module;
import dagger.Provides;

@Module
public class SlackClientModule {

    @Provides
    public SlackPort providesSlackPort(HttpPort client) {
        return new SlackClientAdaptor(client);
    }
}
