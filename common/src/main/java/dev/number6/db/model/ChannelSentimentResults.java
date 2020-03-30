package dev.number6.db.model;

import com.amazonaws.services.comprehend.model.DetectSentimentResult;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ChannelSentimentResults {

    private final Map<Channel, Collection<DetectSentimentResult>> channelResults = new HashMap<>();

    public void addResultsForChannel(Channel c, Collection<DetectSentimentResult> results) {
        channelResults.put(c, results);
    }

    public Set<Channel> getChannels() {
        return channelResults.keySet();
    }

    public Collection<DetectSentimentResult> getResultsForChannel(Channel c) {
        return channelResults.get(c);
    }
}
