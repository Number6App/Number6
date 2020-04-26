package dev.number6.comprehend.port

import dev.number6.comprehend.results.PresentableEntityResults
import dev.number6.comprehend.results.PresentableKeyPhrasesResults
import dev.number6.comprehend.results.PresentableSentimentResults
import dev.number6.message.ChannelMessages

interface ComprehensionPort {
    fun getEntitiesForSlackMessages(channelMessages: ChannelMessages): PresentableEntityResults
    fun getSentimentForSlackMessages(channelMessages: ChannelMessages): PresentableSentimentResults
    fun getKeyPhrasesForSlackMessages(channelMessages: ChannelMessages): PresentableKeyPhrasesResults
}