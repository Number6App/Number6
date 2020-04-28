package dev.number6.slackposter.dagger

import com.amazonaws.services.lambda.runtime.LambdaLogger
import dagger.Module
import dagger.Provides
import dev.number6.slack.port.SecretsPort
import okhttp3.*
import org.apache.http.entity.ContentType

@Module
class FakeHttpClientModule {

    companion object {
        const val SLACK_TOKEN_SECRET = "SlackTokenSecret"
    }

    @Provides
    fun providesFakeSecretsPort(): SecretsPort {
        return object : SecretsPort {
            override fun getSlackTokenSecret(logger: LambdaLogger): String {
                return SLACK_TOKEN_SECRET
            }
        }
    }

    @Provides
    fun providesFakeCallFactory(): Call.Factory {
        return FakeCallFactory()
    }

    internal class FakeCallFactory : Call.Factory {
        internal companion object {
            const val FAKE_CALL_RESPONSE_BODY = "ResponseBody"
        }

        override fun newCall(request: Request): Call {
            return object : Call {
                override fun request(): Request {
                    throw UnsupportedOperationException("request not supported in fake Call")
                }

                override fun execute(): Response {

                    val responseBody = ResponseBody.create(MediaType.parse(ContentType.APPLICATION_JSON.toString()),
                            FAKE_CALL_RESPONSE_BODY)
                    return Response.Builder()
                            .protocol(Protocol.HTTP_1_1)
                            .request(Request.Builder().url("http://localhost:8080").build())
                            .code(200)
                            .message("message")
                            .body(responseBody)
                            .build()
                }

                override fun enqueue(responseCallback: Callback) {
                    throw UnsupportedOperationException("enqueue not supported in fake Call")
                }

                override fun cancel() {
                    throw UnsupportedOperationException("cancel not supported in fake Call")
                }

                override fun isExecuted(): Boolean {
                    throw UnsupportedOperationException("isExecuted not supported in fake Call")
                }

                override fun isCanceled(): Boolean {
                    throw UnsupportedOperationException("isCanceled not supported in fake Call")
                }

                override fun clone(): Call {
                    throw UnsupportedOperationException("clone not supported in fake Call")
                }
            }
        }
    }
}