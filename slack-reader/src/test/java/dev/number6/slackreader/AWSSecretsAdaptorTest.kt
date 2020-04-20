package dev.number6.slackreader

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.amazonaws.services.lambda.runtime.LambdaLogger
import com.amazonaws.services.secretsmanager.AWSSecretsManager
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult
import dev.number6.slack.adaptor.AWSSecretsAdaptor
import dev.number6.slack.port.SecretsConfigurationPort

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import uk.org.fyodor.generators.RDG

internal class AWSSecretsAdaptorTest {
    private val config = Mockito.mock(SecretsConfigurationPort::class.java)
    private val aws = Mockito.mock(AWSSecretsManager::class.java)
    private var testee: AWSSecretsAdaptor? = null
    private var result: GetSecretValueResult? = null
    private val logger = Mockito.mock(LambdaLogger::class.java)

    @BeforeEach
    fun setup() {
        result = GetSecretValueResult().withSecretString(SECRET_TOKEN)
        Mockito.`when`(config.slackTokenSecretName).thenReturn(SECRET_NAME)
        Mockito.`when`(aws.getSecretValue(ArgumentMatchers.any())).thenReturn(result)
        testee = AWSSecretsAdaptor(aws, config)
    }

    @Test
    fun getSecretFromAWS() {
        val secret = testee?.getSlackTokenSecret(logger)
        assertThat(secret).isEqualTo(SECRET_TOKEN)
    }

    @Test
    fun cacheSecretForFutureCalls() {
        assertThat(testee?.getSlackTokenSecret(logger)).isEqualTo(SECRET_TOKEN)
        result = GetSecretValueResult().withSecretString(RDG.string().next())
        Mockito.`when`(aws.getSecretValue(ArgumentMatchers.any())).thenReturn(result)
        assertThat(testee?.getSlackTokenSecret(logger)).isEqualTo(SECRET_TOKEN)
        assertThat(testee?.getSlackTokenSecret(logger)).isEqualTo(SECRET_TOKEN)
    }

    companion object {
        private val SECRET_NAME: String? = "Secret Name"
        private val SECRET_TOKEN: String? = "Secret Token"
    }
}