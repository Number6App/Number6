package dev.number6.slack.adaptor

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.amazonaws.services.lambda.runtime.LambdaLogger
import dev.number6.slack.port.SecretsPort
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import okhttp3.*
import org.apache.http.HttpHeaders
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.io.IOException

@ExtendWith(MockKExtension::class)
internal class OkHttpAdaptorTest {
    private val call: Call = mockk()
    private val logger: LambdaLogger = mockk()
    private val client: Call.Factory = mockk()
    private val secretsPort: SecretsPort = mockk()

    val testee: OkHttpAdaptor = OkHttpAdaptor(client, secretsPort)

    @Test
    @Throws(IOException::class)
    fun addAuthBearerTokenToRequests() {
        every { client.newCall(any()) } returns call
        every { call.execute() } returns Response.Builder()
                .body(ResponseBody.create(MediaType.parse("application/json"), "{}"))
                .request(Request.Builder().url("http://google.com").build())
                .protocol(Protocol.HTTP_1_1)
                .code(200)
                .message("message")
                .build()
        val requestCaptor = slot<Request>()
        every { secretsPort.getSlackTokenSecret(logger) } returns "secret"
        testee.get("http://google.com", logger)
        verify { client.newCall(capture(requestCaptor)) }
        assertThat(requestCaptor.captured.header(HttpHeaders.AUTHORIZATION)).isEqualTo("Bearer secret")
    }
}