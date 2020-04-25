package dev.number6.slack.adaptor

import com.amazonaws.services.lambda.runtime.LambdaLogger
import dev.number6.slack.port.SecretsPort
import okhttp3.*
import org.apache.http.HttpHeaders
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.io.IOException

@ExtendWith(MockitoExtension::class)
@Disabled("replace Mockito")
internal class OkHttpAdaptorTest {
    @Mock
    var responseBody: ResponseBody? = null

    @Mock
    var call: Call? = null

    @Mock
    var logger: LambdaLogger? = null

    @Mock
    var client: Call.Factory? = null

    @Mock
    var secretsPort: SecretsPort? = null

    @InjectMocks
    var testee: OkHttpAdaptor? = null

    @Test
    @Throws(IOException::class)
    fun addAuthBearerTokenToRequests() {
        Mockito.`when`(client!!.newCall(ArgumentMatchers.any())).thenReturn(call)
        Mockito.`when`(call!!.execute()).thenReturn(Response.Builder()
                .body(ResponseBody.create(MediaType.parse("application/json"), "{}"))
                .request(Request.Builder().url("http://google.com").build())
                .protocol(Protocol.HTTP_1_1)
                .code(200)
                .message("message")
                .build())
        val requestCaptor = ArgumentCaptor.forClass(Request::class.java)
        Mockito.`when`(secretsPort!!.getSlackTokenSecret(logger!!)).thenReturn("secret")
        testee!!.get("http://google.com", logger!!)
        Mockito.verify(client)?.newCall(requestCaptor.capture())
        Assertions.assertThat(requestCaptor.value.header(HttpHeaders.AUTHORIZATION)).isEqualTo("Bearer secret")
    }
}