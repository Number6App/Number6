package dev.number6.comprehend.port;

import dev.number6.comprehend.results.PresentableEntityResults;
import dev.number6.comprehend.results.PresentableKeyPhrasesResults;
import dev.number6.comprehend.results.PresentableSentimentResults;
import dev.number6.message.ChannelMessages;

public interface ComprehensionPort {

    PresentableEntityResults getEntitiesForSlackMessages(ChannelMessages channelMessages);

    PresentableSentimentResults getSentimentForSlackMessages(ChannelMessages channelMessages);

    PresentableKeyPhrasesResults getKeyPhrasesForSlackMessages(ChannelMessages channelMessages);
}
