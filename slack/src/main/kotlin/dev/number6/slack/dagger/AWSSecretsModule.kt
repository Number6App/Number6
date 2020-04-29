package dev.number6.slack.dagger

import com.amazonaws.services.secretsmanager.AWSSecretsManager
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder
import dagger.Module
import dagger.Provides
import dev.number6.slack.adaptor.AWSSecretsAdaptor
import dev.number6.slack.adaptor.EnvironmentVariableSlackConfigurationAdaptor
import dev.number6.slack.port.SlackConfigurationPort
import dev.number6.slack.port.SecretsPort

@Module
internal class AWSSecretsModule {
    @Provides
    fun secretsConfiguration(): SlackConfigurationPort {
        return EnvironmentVariableSlackConfigurationAdaptor()
    }

    @Provides
    fun secretsPort(awsSecretsManager: AWSSecretsManager, config: SlackConfigurationPort): SecretsPort {
        return AWSSecretsAdaptor(awsSecretsManager, config)
    }

    @Provides
    fun awsSecretsManager(): AWSSecretsManager {
        return AWSSecretsManagerClientBuilder.defaultClient()
    }
}