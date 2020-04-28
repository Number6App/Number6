package dev.number6.slack.port

internal interface SecretsConfigurationPort {
    fun getSlackTokenSecretName(): String
}