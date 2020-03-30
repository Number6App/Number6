package dev.number6.slackposter.dagger;

import dev.number6.slack.port.HttpPort;
import dagger.Component;

import javax.inject.Singleton;

@Component(modules = {SlackPortModule.class,
        FakeSlackPosterConfigurationModule.class,
        FakeHttpModule.class})
@Singleton
public interface TestSlackPosterComponent extends SlackPosterComponent {

    HttpPort getRecordingHttpAdaptor();

}
