package dev.number6.slackposter;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ChannelSummaryImage {

    public static final int FINAL_UPDATE_MINIMUM_VERSION = 4;
    public static final String CHANNEL_NAME_KEY = "channelName";
    public static final String COMPREHENSION_DATE_KEY = "comprehensionDate";
    public static final String VERSION_KEY = "version";
    public static final String SENTIMENT_TOTALS_KEY = "sentimentTotals";
    public static final String ENTITY_TOTALS_KEY = "entityTotals";
    public static final String KEYPHRASES_TOTALS_KEY = "keyPhrasesTotals";
    public static final String SENTIMENT_SCORE_TOTALS = "sentimentScoreTotals";

    private Map<String, AttributeValue> vals;

    public ChannelSummaryImage(Map<String, AttributeValue> vals) {
        this.vals = vals;
    }

    public boolean hasFinalUpdate() {
        return Integer.parseInt(vals.get(VERSION_KEY).getN()) >= FINAL_UPDATE_MINIMUM_VERSION;
    }

    public Map<String, Double> getSentimentScoreTotals() {
        Map<String, AttributeValue> sentimentScoreTotals = vals.get(SENTIMENT_SCORE_TOTALS).getM();
        return sentimentScoreTotals.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        e -> Double.parseDouble(e.getValue().getN())));
    }

    public Map<String, Integer> getSentimentTotals() {
        Map<String, AttributeValue> sentimentTotals = vals.get(SENTIMENT_TOTALS_KEY).getM();
        return sentimentTotals.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        e -> Integer.parseInt(e.getValue().getN())));
    }

    public Map<String, Integer> getKeyPhrasesTotals() {
        Map<String, AttributeValue> keyPhrasesTotals = vals.get(KEYPHRASES_TOTALS_KEY).getM();
        return keyPhrasesTotals.keySet().stream()
                .collect(Collectors.toMap(Function.identity(),
                        e -> Integer.parseInt(keyPhrasesTotals.get(e).getN())));
    }

    public String getChannelName() {
        return vals.get(CHANNEL_NAME_KEY).getS();
    }

    public String getComprehensionDate() {
        return vals.get(COMPREHENSION_DATE_KEY).getS();
    }

    public Map<String, Map<String, Integer>> getEntityTotals() {
        Map<String, AttributeValue> entityTotals = vals.get(ENTITY_TOTALS_KEY).getM();
        return entityTotals.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        e -> e.getValue().getM().entrySet().stream()
                                .collect(Collectors.toMap(Map.Entry::getKey,
                                        e2 -> Integer.parseInt(e2.getValue().getN())))));
    }

    @Override
    public String toString() {
        return "ChannelSummaryImage{" +
                "vals=" + vals +
                '}';
    }
}
