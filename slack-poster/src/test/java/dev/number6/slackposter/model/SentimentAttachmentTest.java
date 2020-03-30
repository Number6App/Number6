package dev.number6.slackposter.model;

import dev.number6.slackposter.ChannelSummaryImage;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class SentimentAttachmentTest {

    @Test
    void createsFieldsWithExpectedValues() {
        String expectedPretext = "*Message Sentiments:* Neutral: 12, Positive: 6, Negative: 5" + System.lineSeparator();
        Map<String, String> expectedSentimentScoreTotals = new HashMap<>();
        expectedSentimentScoreTotals.put("Mixed", "0.140%");
        expectedSentimentScoreTotals.put("Negative", "36.6%");
        expectedSentimentScoreTotals.put("Neutral", "56.9%");
        expectedSentimentScoreTotals.put("Positive", "6.43%");

        Map<String, AttributeValue> sentimentTotals = new HashMap<>();
        sentimentTotals.put("NEGATIVE", new AttributeValue().withN("5"));
        sentimentTotals.put("NEUTRAL", new AttributeValue().withN("12"));
        sentimentTotals.put("POSITIVE", new AttributeValue().withN("6"));

        Map<String, AttributeValue> sentimentScoreTotals = new HashMap<>();
        sentimentScoreTotals.put("NEGATIVE", new AttributeValue().withN("5.3289502"));
        sentimentScoreTotals.put("NEUTRAL", new AttributeValue().withN("8.283829"));
        sentimentScoreTotals.put("POSITIVE", new AttributeValue().withN("0.93746432"));
        sentimentScoreTotals.put("MIXED", new AttributeValue().withN("0.0203283278"));

        ChannelSummaryImage image = ChannelSummaryImageBuilder.builder()
                .sentimentTotals(sentimentTotals)
                .sentimentScoreTotals(sentimentScoreTotals)
                .build();
        SentimentAttachment testee = new SentimentAttachment(image);

        assertThat(testee.pretext).isEqualTo(expectedPretext);
        assertThat(testee.fields).hasSize(4).extracting("title")
                .containsExactlyInAnyOrder("Negative", "Mixed", "Neutral", "Positive");

        Stream.of(testee.fields).forEach(f ->
                assertThat(f.getValue()).isEqualTo(expectedSentimentScoreTotals.get(f.getTitle())));
    }

}