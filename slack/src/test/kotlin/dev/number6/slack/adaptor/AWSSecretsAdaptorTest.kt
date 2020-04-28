package dev.number6.slack.adaptor

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.amazonaws.services.lambda.runtime.LambdaLogger
import com.amazonaws.services.secretsmanager.AWSSecretsManager
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult
import dev.number6.slack.port.SecretsConfigurationPort
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import uk.org.fyodor.generators.RDG

@ExtendWith(MockKExtension::class)
internal class AWSSecretsAdaptorTest {
    private val config = mockk<SecretsConfigurationPort>()
    private val aws = mockk<AWSSecretsManager>()
    private var testee: AWSSecretsAdaptor = AWSSecretsAdaptor(aws, config)
    private var result: GetSecretValueResult = GetSecretValueResult().withSecretString(SECRET_TOKEN)
    private val logger = mockk<LambdaLogger>()

    @BeforeEach
    fun setup() {
        every { config.getSlackTokenSecretName() } returns SECRET_NAME
        every { aws.getSecretValue(any()) } returns result
        testee = AWSSecretsAdaptor(aws, config)
    }

    @Test
    fun getSecretFromAWS() {
        val secret = testee.getSlackTokenSecret(logger)
        assertThat(secret).isEqualTo(SECRET_TOKEN)
    }

    @Test
    fun cacheSecretForFutureCalls() {
        assertThat(testee.getSlackTokenSecret(logger)).isEqualTo(SECRET_TOKEN)
        val newResult = GetSecretValueResult().withSecretString(RDG.string().next())
        every { aws.getSecretValue(any()) } returns newResult
        assertThat(testee.getSlackTokenSecret(logger)).isEqualTo(SECRET_TOKEN)
        assertThat(testee.getSlackTokenSecret(logger)).isEqualTo(SECRET_TOKEN)
    }

    companion object {
        private const val SECRET_NAME: String = "Secret Name"
        private const val SECRET_TOKEN: String = "Secret Token"
    }
}