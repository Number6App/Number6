package dev.number6.slackposter.dagger

import com.amazonaws.services.lambda.runtime.LambdaLogger
import dev.number6.slack.CallResponse
import dev.number6.slack.port.HttpPort
import java.util.*

class RecordingHttpAdaptor : HttpPort {
    private val posts: MutableMap<String?, String?> = HashMap()
    override fun get(url: String, logger: LambdaLogger): CallResponse {
        return CallResponse("quack")
    }

    override fun post(url: String, body: String, logger: LambdaLogger): CallResponse {
        posts[url] = body
        return CallResponse("quack")
    }

    fun getPosts(): Map<String?, String?> {
        return posts
    }
}