package dev.number6.slack.adaptor

import com.amazonaws.services.lambda.runtime.LambdaLogger
import com.amazonaws.services.secretsmanager.AWSSecretsManager
import com.amazonaws.services.secretsmanager.model.*
import dev.number6.slack.port.SecretsPort
import dev.number6.slack.port.SlackConfigurationPort

internal class AWSSecretsAdaptor(private val aws: AWSSecretsManager, private val config: SlackConfigurationPort) : SecretsPort {

    private var secret: String? = null
    override fun getSlackTokenSecret(logger: LambdaLogger): String {
        return retrieveSecretKeyValue(logger)
    }

    private fun retrieveSecretKeyValue(logger: LambdaLogger): String {
        if (secret == null) {
            val secretName = config.slackTokenSecretName
            val getSecretValueRequest = GetSecretValueRequest().withSecretId(secretName)
            var getSecretValueResult: GetSecretValueResult? = null
            try {
                getSecretValueResult = aws.getSecretValue(getSecretValueRequest)
            } catch (e: ResourceNotFoundException) {
                logger.log("The requested secret $secretName was not found")
            } catch (e: InvalidRequestException) {
                logger.log("The request was invalid due to: " + e.message)
            } catch (e: InvalidParameterException) {
                logger.log("The request had invalid params: " + e.message)
            }
            if (getSecretValueResult == null) {
                logger.log("No secret found, returning empty string")
                return ""
            }

            // Decrypted secret using the associated KMS CMK
            // Depending on whether the secret was a string or binary, one of these fields will be populated
            secret = if (getSecretValueResult.secretString != null) {
                getSecretValueResult.secretString
            } else {
                getSecretValueResult.secretBinary.toString()
            }
        }
        return secret!!
    }

}