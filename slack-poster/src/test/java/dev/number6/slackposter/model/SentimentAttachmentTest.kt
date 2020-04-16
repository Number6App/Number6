package dev.number6.slackposter.model

import com.amazonaws.services.dynamodbv2.model.AttributeValue
import dev.number6.slackposter.ChannelSummaryImage
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.util.*
import java.util.stream.Stream

internal class SentimentAttachmentTest {
    @Test
    fun createsFieldsWithExpectedValues() {
        val expectedPretext = "*Message Sentiments:* Neutral: 12, Positive: 6, Negative: 5" + System.lineSeparator()
        val expectedSentimentScoreTotals: MutableMap<String?, String> = HashMap()
        expectedSentimentScoreTotals["Mixed"] = "0.140%"
        expectedSentimentScoreTotals["Negative"] = "36.6%"
        expectedSentimentScoreTotals["Neutral"] = "56.9%"
        expectedSentimentScoreTotals["Positive"] = "6.43%"
        val sentimentTotals: MutableMap<String, AttributeValue?> = HashMap()
        sentimentTotals["NEGATIVE"] = AttributeValue().withN("5")
        sentimentTotals["NEUTRAL"] = AttributeValue().withN("12")
        sentimentTotals["POSITIVE"] = AttributeValue().withN("6")
        val sentimentScoreTotals: MutableMap<String, AttributeValue?> = HashMap()
        sentimentScoreTotals["NEGATIVE"] = AttributeValue().withN("5.3289502")
        sentimentScoreTotals["NEUTRAL"] = AttributeValue().withN("8.283829")
        sentimentScoreTotals["POSITIVE"] = AttributeValue().withN("0.93746432")
        sentimentScoreTotals["MIXED"] = AttributeValue().withN("0.0203283278")
        val image: ChannelSummaryImage = ChannelSummaryImageBuilder.Companion.builder()
                .sentimentTotals(sentimentTotals)
                .sentimentScoreTotals(sentimentScoreTotals)
                .build()
        val testee = SentimentAttachment.fromImage(image)
        Assertions.assertThat(testee.pretext).isEqualTo(expectedPretext)
        Assertions.assertThat(testee.fields).hasSize(4).extracting("title")
                .containsExactlyInAnyOrder("Negative", "Mixed", "Neutral", "Positive")
        Stream.of(*testee.fields).forEach { f: Field -> Assertions.assertThat(f.value).isEqualTo(expectedSentimentScoreTotals[f.title]) }
    }
}