package dev.number6.keyphrases.adaptor

import dev.number6.comprehend.port.ComprehensionPort
import dev.number6.comprehend.results.PresentableEntityResults
import dev.number6.comprehend.results.PresentableKeyPhrasesResults
import dev.number6.comprehend.results.PresentableSentimentResults
import dev.number6.message.ChannelMessages

class FakeComprehensionAdaptor : ComprehensionPort {
    override fun getEntitiesForSlackMessages(channelMessages: ChannelMessages): PresentableEntityResults {
        throw UnsupportedOperationException("cannot get entities in Fake Comprehension Adaptor")
    }

    override fun getSentimentForSlackMessages(channelMessages: ChannelMessages): PresentableSentimentResults {
        throw UnsupportedOperationException("cannot get sentiment in Fake Comprehension Adaptor")
    }

    override fun getKeyPhrasesForSlackMessages(channelMessages: ChannelMessages): PresentableKeyPhrasesResults {
        throw UnsupportedOperationException("cannot get key phrases in Fake Comprehension Adaptor")
    }
}