package dev.number6.slack.dagger

import com.amazonaws.services.lambda.runtime.LambdaLogger
import com.amazonaws.services.secretsmanager.AWSSecretsManager
import com.amazonaws.services.secretsmanager.AbstractAWSSecretsManager
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult
import dagger.Module
import dagger.Provides
import dev.number6.slack.port.SecretsConfigurationPort
import dev.number6.slack.port.SecretsPort

@Module
internal class FakeAWSSecretsManagerModule {
    @Provides
    fun providesFakeSecretsPort(): SecretsPort {
        return object : SecretsPort {
            override fun getSlackTokenSecret(logger: LambdaLogger): String {
                return SLACK_TOKEN_SECRET
            }
        }
    }

    @Provides
    fun providesSecretsConfig(): SecretsConfigurationPort {
        return object : SecretsConfigurationPort {
            override fun getSlackTokenSecretName(): String {
                return SLACK_TOKEN_SECRET_NAME
            }
        }
    }

    @Provides
    fun providesFakeSecretsManager(config: SecretsConfigurationPort): AWSSecretsManager {
        return FakeSecretsManager(config)
    }

    private inner class FakeSecretsManager internal constructor(private val config: SecretsConfigurationPort) : AbstractAWSSecretsManager() {
        override fun getSecretValue(request: GetSecretValueRequest): GetSecretValueResult {
            return GetSecretValueResult().withSecretString("{\"" + config.getSlackTokenSecretName() + "\":\"secret_value\"}")
        }

    }

    companion object {
        private const val SLACK_TOKEN_SECRET_NAME = "SlackTokenSecretName"
        private const val SLACK_TOKEN_SECRET = "SlackTokenSecret"
    }
}