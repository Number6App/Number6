package dev.number6.slackposter.dagger;

import dev.number6.slack.dagger.AWSSecretsModule;
import dev.number6.slack.dagger.HttpModule;
import dev.number6.slackposter.SlackService;
import dagger.Component;

@Component(modules = {SlackPortModule.class,
        SlackPosterConfigurationModule.class,
        HttpModule.class})
public interface SlackPosterComponent {

    SlackService handler();
}
