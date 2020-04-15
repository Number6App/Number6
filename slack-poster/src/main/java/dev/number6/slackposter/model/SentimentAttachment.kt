package dev.number6.slackposter.model

import dev.number6.slackposter.ChannelSummaryImage
import java.math.BigDecimal
import java.math.MathContext
import java.text.MessageFormat
import java.util.*
import java.util.Map.Entry.comparingByValue
import java.util.function.Function
import java.util.stream.Collectors

class SentimentAttachment internal constructor(image: ChannelSummaryImage) : Attachment() {
    companion object {
        const val SENTIMENT_MESSAGE_TOTAL_FORMAT = "{0}: {1}"
    }

    init {
        fallback = "Overall Sentiment Totals."
        color = "#2eb886"
        title = "Overall Sentiment Totals:"
        val sentimentScoreTotals = image.sentimentScoreTotals
        val sentimentScoreTotal = sentimentScoreTotals.entries.stream()
                .mapToDouble { d -> d.value }
                .sum()
        val fields = sentimentScoreTotals.entries.stream()
                .map { e -> Field(asTitleCase(e.key), BigDecimal(100.0 * e.value / sentimentScoreTotal, MathContext(3)).toString() + "%", true) }
                .sorted(Comparator.comparing(Function<Field,String> { e -> e.value }))
                .collect(Collectors.toList())
        val sentimentMessage = image.sentimentTotals.entries.stream()
                .sorted(comparingByValue { i1: Int, i2: Int -> i2 - i1 })
                .map { e -> MessageFormat.format(SENTIMENT_MESSAGE_TOTAL_FORMAT, asTitleCase(e.key), e.value) }
                .collect(Collectors.joining(", "))
        pretext = "*Message Sentiments:* " + sentimentMessage + System.lineSeparator()
        this.fields = fields.toTypedArray()
    }
}