package dev.number6.slack.dagger

import com.amazonaws.services.lambda.runtime.LambdaLogger
import com.amazonaws.services.secretsmanager.AWSSecretsManager
import com.amazonaws.services.secretsmanager.AbstractAWSSecretsManager
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult
import dagger.Module
import dagger.Provides
import dev.number6.slack.port.SecretsPort
import dev.number6.slack.port.SlackConfigurationPort

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
    fun providesSecretsConfig(): SlackConfigurationPort {
        return object : SlackConfigurationPort {
            override val slackTokenSecretName: String
                get() = SLACK_TOKEN_SECRET_NAME
            override val slackPostMessageUrl: String
                get() = SLACK_POST_MESSAGE_URL
        }
    }

    @Provides
    fun providesFakeSecretsManager(config: SlackConfigurationPort): AWSSecretsManager {
        return FakeSecretsManager(config)
    }

    private inner class FakeSecretsManager internal constructor(private val config: SlackConfigurationPort) : AbstractAWSSecretsManager() {
        override fun getSecretValue(request: GetSecretValueRequest): GetSecretValueResult {
            return GetSecretValueResult().withSecretString("{\"" + config.slackTokenSecretName + "\":\"secret_value\"}")
        }
    }

    companion object {
        private const val SLACK_TOKEN_SECRET_NAME = "SlackTokenSecretName"
        private const val SLACK_TOKEN_SECRET = "SlackTokenSecret"
        private const val SLACK_POST_MESSAGE_URL = "http://post.me.to.slack.com"
    }
}