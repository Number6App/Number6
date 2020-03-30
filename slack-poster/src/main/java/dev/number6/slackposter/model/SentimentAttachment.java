package dev.number6.slackposter.model;

import dev.number6.slackposter.ChannelSummaryImage;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.MessageFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SentimentAttachment extends Attachment {

    public static final String SENTIMENT_MESSAGE_TOTAL_FORMAT = "{0}: {1}";

    SentimentAttachment(ChannelSummaryImage image) {
        this.fallback = "Overall Sentiment Totals.";
        this.color = "#2eb886";
        this.title = "Overall Sentiment Totals:";

        Map<String, Double> sentimentScoreTotals = image.getSentimentScoreTotals();
        double sentimentScoreTotal = sentimentScoreTotals.values().stream()
                .mapToDouble(Double::doubleValue)
                .sum();

        List<Field> fields = sentimentScoreTotals.entrySet().stream()
                .map(e -> new Field(asTitleCase(e.getKey()), new BigDecimal(100d * e.getValue() / sentimentScoreTotal, new MathContext(3)).toString() + "%", true))
                .sorted(Comparator.comparing(Field::getValue))
                .collect(Collectors.toList());

        String sentimentMessage = image.getSentimentTotals().entrySet().stream()
                .sorted(Map.Entry.comparingByValue((i1, i2) -> i2 - i1))
                .map(e -> MessageFormat.format(SENTIMENT_MESSAGE_TOTAL_FORMAT, asTitleCase(e.getKey()), e.getValue()))
                .collect(Collectors.joining(", "));

        this.pretext = "*Message Sentiments:* " + sentimentMessage + System.lineSeparator();
        this.fields = fields.toArray(new Field[]{});
    }
}
