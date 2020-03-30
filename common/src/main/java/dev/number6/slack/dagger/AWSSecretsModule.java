package dev.number6.slack.dagger;

import dev.number6.slack.adaptor.AWSSecretsAdaptor;
import dev.number6.slack.adaptor.EnvironmentVariableSecretsConfigurationAdaptor;
import dev.number6.slack.port.SecretsConfigurationPort;
import dev.number6.slack.port.SecretsPort;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import dagger.Module;
import dagger.Provides;

@Module
public class AWSSecretsModule {

    @Provides
    public SecretsConfigurationPort secretsConfiguration() {
        return new EnvironmentVariableSecretsConfigurationAdaptor();
    }

    @Provides
    public SecretsPort secretsPort(AWSSecretsManager awsSecretsManager, SecretsConfigurationPort config) {
        return new AWSSecretsAdaptor(awsSecretsManager, config);
    }

    @Provides
    public AWSSecretsManager awsSecretsManager() {
        return AWSSecretsManagerClientBuilder.defaultClient();
    }
}
