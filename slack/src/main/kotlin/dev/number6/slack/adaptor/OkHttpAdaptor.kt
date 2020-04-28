package dev.number6.slack.adaptor

import com.amazonaws.services.lambda.runtime.LambdaLogger
import dev.number6.slack.CallResponse
import dev.number6.slack.port.HttpPort
import dev.number6.slack.port.SecretsPort
import okhttp3.Call
import okhttp3.MediaType
import okhttp3.Request
import okhttp3.RequestBody
import org.apache.http.HttpHeaders
import org.apache.http.entity.ContentType
import java.io.IOException
import java.util.*
import javax.inject.Inject

internal class OkHttpAdaptor(private val client: Call.Factory,
                             private val secretsPort: SecretsPort) : HttpPort {
    private fun makeHttpCall(request: OkHttpRequestAdaptor): CallResponse {
        return try {
            CallResponse(Objects.requireNonNull(client.newCall(request.valueOf()).execute().body())!!.string())
        } catch (e: IOException) {
            CallResponse(e)
        }
    }

    override fun get(url: String, logger: LambdaLogger): CallResponse {
        return makeHttpCall(buildGetRequestForUrl(url, logger))
    }

    override fun post(url: String, body: String, logger: LambdaLogger): CallResponse {
        return makeHttpCall(buildPostRequestForUrl(url, body, logger))
    }

    private fun buildGetRequestForUrl(url: String, logger: LambdaLogger): OkHttpRequestAdaptor {
        return OkHttpRequestAdaptor(buildCommonRequest(url, logger)
                .get()
                .build())
    }

    private fun buildPostRequestForUrl(url: String, body: String, logger: LambdaLogger): OkHttpRequestAdaptor {
        var builder = buildCommonRequest(url, logger)
        if (!body.isEmpty()) {
            builder = builder.post(RequestBody.create(MediaType.parse("application/json"), body))
        }
        return OkHttpRequestAdaptor(builder.build())
    }

    private fun buildCommonRequest(url: String, logger: LambdaLogger): Request.Builder {
        return Request.Builder()
                .addHeader(HttpHeaders.AUTHORIZATION, String.format(BEARER_TOKEN_VALUE, secretsPort.getSlackTokenSecret(logger)))
                .addHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_FORM_URLENCODED.toString())
                .url(url)
    }

    companion object {
        const val BEARER_TOKEN_VALUE = "Bearer %s"
    }

}