package dev.number6.slackreader.dagger;

import dev.number6.slack.port.HttpPort;
import dev.number6.slackreader.adaptor.SlackReaderAdaptor;
import dev.number6.slackreader.port.SlackPort;
import dagger.Module;
import dagger.Provides;

@Module
public class SlackPortModule {

    @Provides
    public SlackPort slackClient(HttpPort httpPort) {
        return new SlackReaderAdaptor(httpPort);
    }
}
