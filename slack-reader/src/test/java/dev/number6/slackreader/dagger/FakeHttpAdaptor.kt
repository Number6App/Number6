package dev.number6.slackreader.dagger

import com.amazonaws.services.lambda.runtime.LambdaLogger
import com.google.gson.Gson
import dev.number6.slack.CallResponse
import dev.number6.slack.adaptor.SlackClientAdaptor
import dev.number6.slack.port.HttpPort
import dev.number6.slackreader.generate.SlackReaderRDG
import dev.number6.slackreader.model.JoinChannelResponse
import uk.org.fyodor.generators.Generator

class FakeHttpAdaptor : HttpPort {
    private val gson = Gson()
    private val response: Map<String, Generator<String>>
    override fun get(url: String, logger: LambdaLogger): CallResponse {
        val next = response[urlBeforeParameters(url)]?.next() ?: ""
        return CallResponse(next)
    }

    override fun post(url: String, body: String, logger: LambdaLogger): CallResponse {
        return CallResponse(response[urlBeforeParameters(url)]?.next() ?: "")
    }

    private fun urlBeforeParameters(url: String): String {
        return url.split("\\?".toRegex()).toTypedArray()[0]
    }

    init {
        response = mapOf(SlackClientAdaptor.CHANNEL_LIST_URL to SlackReaderRDG.jsonChannelsListResponse(),
                urlBeforeParameters(SlackClientAdaptor.CHANNEL_HISTORY_URL) to SlackReaderRDG.jsonChannelHistoryResponse(),
                urlBeforeParameters(SlackClientAdaptor.JOIN_CHANNEL_URL) to Generator { gson.toJson(JoinChannelResponse.ok()) })
    }
}