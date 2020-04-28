package dev.number6.slack

import com.amazonaws.services.lambda.runtime.LambdaLogger
import dev.number6.slack.port.HttpPort
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Request
import okhttp3.Response

class FakeHttpAdaptor : HttpPort {
    private var response = "response"
    override fun get(url: String, logger: LambdaLogger): CallResponse {
        return CallResponse(response)
    }

    override fun post(url: String, body: String, logger: LambdaLogger): CallResponse {
        return CallResponse(response)
    }

    fun setResponse(response: String) {
        this.response = response
    }
}