package dev.number6.entity.adaptor;

import dev.number6.comprehend.port.ComprehensionPort;
import dev.number6.comprehend.results.PresentableEntityResults;
import dev.number6.comprehend.results.PresentableKeyPhrasesResults;
import dev.number6.comprehend.results.PresentableSentimentResults;
import dev.number6.message.ChannelMessages;

public class FakeComprehensionAdaptor implements ComprehensionPort {

    @Override
    public PresentableEntityResults getEntitiesForSlackMessages(ChannelMessages channelMessages) {
        throw new UnsupportedOperationException("cannot get entities in Fake Comprehension Adaptor");
    }

    @Override
    public PresentableSentimentResults getSentimentForSlackMessages(ChannelMessages channelMessages) {
        throw new UnsupportedOperationException("cannot get sentiment in Fake Comprehension Adaptor");
    }

    @Override
    public PresentableKeyPhrasesResults getKeyPhrasesForSlackMessages(ChannelMessages channelMessages) {
        throw new UnsupportedOperationException("cannot get key phrases in Fake Comprehension Adaptor");
    }
}
