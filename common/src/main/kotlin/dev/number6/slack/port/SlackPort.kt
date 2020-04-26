package dev.number6.slack.port

import com.amazonaws.services.lambda.runtime.LambdaLogger
import java.util.*

interface SlackPort {
    fun getSlackResponse(url: String, logger: LambdaLogger, body: String): Optional<String>
    fun <T> getSlackResponse(url: String, responseType: Class<T>?, logger: LambdaLogger): Optional<T>
    fun <T> getSlackResponse(url: String, body: String?, responseType: Class<T>?, logger: LambdaLogger): Optional<T>
}