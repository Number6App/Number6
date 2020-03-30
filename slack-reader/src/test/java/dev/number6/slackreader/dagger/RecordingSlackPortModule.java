package dev.number6.slackreader.dagger;

import dev.number6.slack.port.HttpPort;
import dev.number6.slackreader.port.SlackPort;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class RecordingSlackPortModule {

    @Provides
    @Singleton
    SlackPort providesSlackPort(HttpPort client) {
        return new RecordingSlackReaderAdaptor(client);
    }
}
