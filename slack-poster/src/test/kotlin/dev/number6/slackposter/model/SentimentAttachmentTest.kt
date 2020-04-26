package dev.number6.slackposter.model

import assertk.assertThat
import assertk.assertions.containsOnly
import assertk.assertions.extracting
import assertk.assertions.hasSize
import assertk.assertions.isEqualTo
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import dev.number6.slackposter.ChannelSummaryImage
import org.junit.jupiter.api.Test

internal class SentimentAttachmentTest {
    @Test
    fun createsFieldsWithExpectedValues() {
        val expectedPretext = "*Message Sentiments:* Neutral: 12, Positive: 6, Negative: 5" + System.lineSeparator()
        val expectedSentimentScoreTotals = mapOf("Mixed" to "0.140%", "Negative" to "36.6%", "Neutral" to "56.9%", "Positive" to "6.43%")

        val sentimentTotals = mapOf("NEGATIVE" to AttributeValue().withN("5"),
                "NEUTRAL" to AttributeValue().withN("12"),
                "POSITIVE" to AttributeValue().withN("6"))

        val sentimentScoreTotals = mapOf("NEGATIVE" to AttributeValue().withN("5.3289502"),
                "NEUTRAL" to AttributeValue().withN("8.283829"),
                "POSITIVE" to AttributeValue().withN("0.93746432"),
                "MIXED" to AttributeValue().withN("0.0203283278"))

        val image: ChannelSummaryImage = ChannelSummaryImageBuilder.builder()
                .sentimentTotals(sentimentTotals)
                .sentimentScoreTotals(sentimentScoreTotals)
                .build()
        val testee = SentimentAttachment.fromImage(image)
        assertThat(testee.pretext).isEqualTo(expectedPretext)
        assertThat(testee.fields).hasSize(4)
        assertThat(testee.fields).extracting { f -> f.title }
                .containsOnly("Negative", "Mixed", "Neutral", "Positive")
        testee.fields.forEach { f -> assertThat(f.value).isEqualTo(expectedSentimentScoreTotals[f.title]) }
    }
}