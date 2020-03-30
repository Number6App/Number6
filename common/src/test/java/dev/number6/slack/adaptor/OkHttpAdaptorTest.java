package dev.number6.slack.adaptor;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import dev.number6.slack.port.SecretsPort;
import okhttp3.*;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OkHttpAdaptorTest {

    @Mock
    ResponseBody responseBody;
    @Mock Call call;
    @Mock
    LambdaLogger logger;
    @Mock
    Call.Factory client;
    @Mock
    SecretsPort secretsPort;
    @InjectMocks
    OkHttpAdaptor testee;

    @Test
    void addAuthBearerTokenToRequests() throws IOException {

        when(client.newCall(any())).thenReturn(call);
        when(call.execute()).thenReturn(new Response.Builder()
                .body(ResponseBody.create(MediaType.parse("application/json"), "{}"))
                .request(new Request.Builder().url("http://google.com").build())
                .protocol(Protocol.HTTP_1_1)
                .code(200)
                .message("message")
                .build());

        ArgumentCaptor<Request> requestCaptor = ArgumentCaptor.forClass(Request.class);
        when(secretsPort.getSlackTokenSecret(logger)).thenReturn("secret");

        testee.get("http://google.com", logger);

        verify(client).newCall(requestCaptor.capture());
        assertThat(requestCaptor.getValue().header(HttpHeaders.AUTHORIZATION)).isEqualTo("Bearer secret");
    }

}