package dev.number6.slack.port

import com.amazonaws.services.lambda.runtime.LambdaLogger
import dev.number6.slack.model.CallResponse

internal interface HttpPort {
    fun get(url: String, logger: LambdaLogger): CallResponse
    fun post(url: String, body: String, logger: LambdaLogger): CallResponse
}