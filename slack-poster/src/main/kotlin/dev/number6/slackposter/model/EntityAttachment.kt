package dev.number6.slackposter.model

import dev.number6.slackposter.ChannelSummaryImage
import java.text.MessageFormat
import java.util.*
import java.util.Map.Entry.comparingByValue
import java.util.stream.Collectors

fun String.formatWithEntry(e: Map.Entry<String, Int>): String {
    return MessageFormat.format(this, e.key, e.value)
}

class EntityAttachment(fallback: String, color: String, title: String, fields: Array<Field>) :
        Attachment(fallback = fallback, color = color, title = title, fields = fields) {

    companion object {
        private const val CHANNEL_ENTITY_DETAILS = "{0} ({1})"

        fun fromImage(image: ChannelSummaryImage): EntityAttachment {
            val fallback = "Entities:"
            val color = "warning"
            val title = "Entities:"
            val fields: MutableList<Field> = ArrayList()
            image.entityTotals.forEach { (k, v) ->
                val entryTitle = k.asTitleCase() + ":"
                val entityLine = v.entries.stream()
                        .sorted(comparingByValue { o1, o2 -> o2 - o1 })
                        .map { e -> CHANNEL_ENTITY_DETAILS.formatWithEntry(e) }
                        .collect(Collectors.joining(", "))
                fields.add(Field(entryTitle, entityLine, false))
            }
            return EntityAttachment(fallback = fallback, color = color, title = title, fields = fields.toTypedArray())
        }
    }
}