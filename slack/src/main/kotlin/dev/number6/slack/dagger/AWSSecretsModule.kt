package dev.number6.slack.dagger

import com.amazonaws.services.secretsmanager.AWSSecretsManager
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder
import dagger.Module
import dagger.Provides
import dev.number6.slack.adaptor.AWSSecretsAdaptor
import dev.number6.slack.adaptor.EnvironmentVariableSecretsConfigurationAdaptor
import dev.number6.slack.port.SecretsConfigurationPort
import dev.number6.slack.port.SecretsPort

@Module
internal class AWSSecretsModule {
    @Provides
    fun secretsConfiguration(): SecretsConfigurationPort {
        return EnvironmentVariableSecretsConfigurationAdaptor()
    }

    @Provides
    fun secretsPort(awsSecretsManager: AWSSecretsManager, config: SecretsConfigurationPort): SecretsPort {
        return AWSSecretsAdaptor(awsSecretsManager, config)
    }

    @Provides
    fun awsSecretsManager(): AWSSecretsManager {
        return AWSSecretsManagerClientBuilder.defaultClient()
    }
}