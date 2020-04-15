package dev.number6.slackposter.model

import dev.number6.slackposter.ChannelSummaryImage
import java.text.MessageFormat
import java.util.Map.Entry.comparingByValue
import java.util.stream.Collectors

class KeyPhrasesAttachment internal constructor(image: ChannelSummaryImage) : Attachment() {
    companion object {
        const val CHANNEL_KEY_PHRASES_DETAILS = "{0} ({1})"
    }

    init {
        fallback = "Key Phrases (occurring more than once)"
        color = "danger"
        title = "Key Phrases (occurring more than once):"
        val keyPhrases = image.keyPhrasesTotals
        val keyPhrasesLine = keyPhrases.entries.stream()
                .filter { e -> e.value > 1 }
                .sorted(comparingByValue { i1, i2 -> i2 - i1 })
                .map { e -> MessageFormat.format(CHANNEL_KEY_PHRASES_DETAILS, e.key, e.value) }
                .collect(Collectors.joining(", "))
        fields = arrayOf(Field("", keyPhrasesLine, false))
    }
}