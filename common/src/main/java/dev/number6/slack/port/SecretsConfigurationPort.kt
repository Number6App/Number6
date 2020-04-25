package dev.number6.slack.port

interface SecretsConfigurationPort {
    fun getSlackTokenSecretName(): String
}