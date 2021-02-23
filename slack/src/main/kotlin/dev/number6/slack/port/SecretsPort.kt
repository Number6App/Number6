package dev.number6.slack.port

import com.amazonaws.services.lambda.runtime.LambdaLogger

interface SecretsPort {
    fun getSlackTokenSecret(logger: LambdaLogger): String
}