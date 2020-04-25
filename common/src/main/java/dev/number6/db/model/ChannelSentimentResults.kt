package dev.number6.db.model

import com.amazonaws.services.comprehend.model.DetectSentimentResult
import java.util.*

class ChannelSentimentResults {
    private val channelResults: MutableMap<Channel, Collection<DetectSentimentResult>> = HashMap()
    fun addResultsForChannel(c: Channel, results: Collection<DetectSentimentResult>) {
        channelResults[c] = results
    }

    val channels: Set<Channel>
        get() = channelResults.keys

    fun getResultsForChannel(c: Channel): Collection<DetectSentimentResult> {
        return channelResults[c]!!
    }
}