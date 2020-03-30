package dev.number6.slack;

import dev.number6.slack.port.SecretsConfigurationPort;
import dev.number6.slack.port.SecretsPort;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AbstractAWSSecretsManager;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import dagger.Module;
import dagger.Provides;

@Module
public class FakeAWSSecretsManagerModule {

    private static final String SLACK_TOKEN_SECRET_NAME = "SlackTokenSecretName";
    private static final String SLACK_TOKEN_SECRET = "SlackTokenSecret";

    @Provides
    public SecretsPort providesFakeSecretsPort() {
        return logger -> SLACK_TOKEN_SECRET;
    }

    @Provides
    public SecretsConfigurationPort providesSecretsConfig() {
        return () -> SLACK_TOKEN_SECRET_NAME;

    }

    @Provides
    public AWSSecretsManager providesFakeSecretsManager(SecretsConfigurationPort config) {
        return new FakeSecretsManager(config);
    }

    private class FakeSecretsManager extends AbstractAWSSecretsManager {

        private final SecretsConfigurationPort config;

        FakeSecretsManager(SecretsConfigurationPort config) {
            this.config = config;
        }

        @Override
        public GetSecretValueResult getSecretValue(GetSecretValueRequest request) {
            return new GetSecretValueResult().withSecretString("{\"" + config.getSlackTokenSecretName() + "\":\"secret_value\"}");
        }
    }
}
