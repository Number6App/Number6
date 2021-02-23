package dev.number6.slack.adaptor

import dev.number6.slack.port.SlackConfigurationPort

internal class EnvironmentVariableSlackConfigurationAdaptor : SlackConfigurationPort {
    override val slackTokenSecretName: String
        get() = System.getenv(SLACK_TOKEN_SECRET_NAME)

    companion object {
        private const val SLACK_TOKEN_SECRET_NAME = "SLACK_TOKEN_SECRET_NAME"
    }
}