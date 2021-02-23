package dev.number6.slackposter.model

import dev.number6.slackposter.ChannelSummaryImage
import java.math.BigDecimal
import java.math.MathContext
import java.text.MessageFormat
import java.util.*
import java.util.Map.Entry.comparingByValue
import java.util.stream.Collectors

class SentimentAttachment(fallback: String, color: String, title: String, pretext: String, fields: Array<Field>) :
        Attachment(fallback = fallback, color = color, title = title, pretext = pretext, fields = fields) {
    companion object {
        private const val SENTIMENT_MESSAGE_TOTAL_FORMAT = "{0}: {1}"

        fun fromImage(image: ChannelSummaryImage): SentimentAttachment {
            val fallback = "Overall Sentiment Totals."
            val color = "#2eb886"
            val title = "Overall Sentiment Totals:"
            val sentimentScoreTotals = image.sentimentScoreTotals
            val sentimentScoreTotal = sentimentScoreTotals.entries.stream()
                    .mapToDouble { d -> d.value }
                    .sum()
            val fields = sentimentScoreTotals.entries.stream()
                    .map { e -> Field(e.key.asTitleCase(), BigDecimal(100.0 * e.value / sentimentScoreTotal, MathContext(3)).toString() + "%", true) }
                    .sorted(Comparator.comparing { e: Field -> e.value })
                    .collect(Collectors.toList())
            val sentimentMessage = image.sentimentTotals.entries.stream()
                    .sorted(comparingByValue { i1: Int, i2: Int -> i2 - i1 })
                    .map { e -> MessageFormat.format(SENTIMENT_MESSAGE_TOTAL_FORMAT, e.key.asTitleCase(), e.value) }
                    .collect(Collectors.joining(", "))
            val pretext = "*Message Sentiments:* " + sentimentMessage + System.lineSeparator()
            return SentimentAttachment(fallback, color, title, pretext, fields.toTypedArray())
        }
    }
}