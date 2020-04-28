package dev.number6.slack.adaptor

import dev.number6.slack.port.SecretsConfigurationPort

internal class EnvironmentVariableSecretsConfigurationAdaptor : SecretsConfigurationPort {
    override fun getSlackTokenSecretName(): String {
        return System.getenv(SLACK_TOKEN_SECRET_NAME)
    }

    companion object {
        private const val SLACK_TOKEN_SECRET_NAME = "SLACK_TOKEN_SECRET_NAME"
    }
}