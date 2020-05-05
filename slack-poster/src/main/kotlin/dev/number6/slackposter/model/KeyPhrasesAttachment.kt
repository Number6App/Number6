package dev.number6.slackposter.model

import dev.number6.slackposter.ChannelSummaryImage
import java.util.Map.Entry.comparingByValue
import java.util.stream.Collectors

class KeyPhrasesAttachment(fallback: String, color: String, title: String, fields: Array<Field>) :
        Attachment(fallback = fallback, color = color, title = title, fields = fields) {
    companion object {
        private const val CHANNEL_KEY_PHRASES_DETAILS = "{0} ({1})"

        fun fromImage(image: ChannelSummaryImage): KeyPhrasesAttachment {
            val fallback = "Key Phrases (occurring more than once)"
            val color = "danger"
            val title = "Key Phrases (occurring more than once):"
            val keyPhrases = image.keyPhrasesTotals
            val keyPhrasesLine = keyPhrases.entries.stream()
                    .filter { e -> e.value > 1 }
                    .sorted(comparingByValue { i1, i2 -> i2 - i1 })
                    .map { e -> CHANNEL_KEY_PHRASES_DETAILS.formatWithEntry(e) }
                    .collect(Collectors.joining(", "))
            val fields = arrayOf(Field("", keyPhrasesLine, false))
            return KeyPhrasesAttachment(fallback, color, title, fields)
        }
    }
}