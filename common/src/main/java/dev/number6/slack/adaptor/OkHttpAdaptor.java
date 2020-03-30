package dev.number6.slack.adaptor;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import dev.number6.slack.CallResponse;
import dev.number6.slack.OkHttpRequestAdaptor;
import dev.number6.slack.port.HttpPort;
import dev.number6.slack.port.SecretsPort;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.apache.http.HttpHeaders;
import org.apache.http.entity.ContentType;

import java.io.IOException;
import java.util.Objects;

public class OkHttpAdaptor implements HttpPort {

    public static final String BEARER_TOKEN_VALUE = "Bearer %s";
    private Call.Factory client;
    private SecretsPort secretsPort;

    public OkHttpAdaptor(Call.Factory client, SecretsPort secretsPort) {
        this.client = client;
        this.secretsPort = secretsPort;
    }

    private CallResponse makeHttpCall(OkHttpRequestAdaptor request) {
        try {
            return new CallResponse(Objects.requireNonNull(client.newCall(request.valueOf()).execute().body()).string());
        } catch (IOException e) {
            return new CallResponse(e);
        }
    }

    @Override
    public CallResponse get(String url, LambdaLogger logger) {
        return makeHttpCall(buildGetRequestForUrl(url, logger));
    }

    @Override
    public CallResponse post(String url, String body, LambdaLogger logger) {
        return makeHttpCall(buildPostRequestForUrl(url, body, logger));
    }


    private OkHttpRequestAdaptor buildGetRequestForUrl(String url, LambdaLogger logger) {
        return new OkHttpRequestAdaptor(buildCommonRequest(url, logger)
                .get()
                .build());
    }

    private OkHttpRequestAdaptor buildPostRequestForUrl(String url, String body, LambdaLogger logger) {
        var builder = buildCommonRequest(url, logger);
        if (!body.isEmpty()) {
            builder = builder.post(RequestBody.create(MediaType.parse("application/json"), body));
        }
        return new OkHttpRequestAdaptor(builder.build());
    }

    private Request.Builder buildCommonRequest(String url, LambdaLogger logger) {
        return new Request.Builder()
                .addHeader(HttpHeaders.AUTHORIZATION, String.format(BEARER_TOKEN_VALUE, secretsPort.getSlackTokenSecret(logger)))
                .addHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_FORM_URLENCODED.toString())
                .url(url);
    }
}
