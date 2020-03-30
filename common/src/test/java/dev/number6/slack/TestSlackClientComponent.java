package dev.number6.slack;

import dev.number6.slack.dagger.SlackClientModule;
import dev.number6.slack.port.HttpPort;
import dev.number6.slack.port.SlackPort;
import dagger.Component;

import javax.inject.Singleton;

@Component(modules = {
        SlackClientModule.class,
        FakeHttpModule.class,
        FakeAWSSecretsManagerModule.class
})
@Singleton
public interface TestSlackClientComponent {

    SlackPort slackPort();

    HttpPort httpPort();
}
