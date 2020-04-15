package dev.number6.slackposter.model

import dev.number6.slackposter.ChannelSummaryImage
import java.text.MessageFormat
import java.util.*
import java.util.stream.Collectors

class EntityAttachment internal constructor(image: ChannelSummaryImage) : Attachment() {
    companion object {
        const val CHANNEL_ENTITY_DETAILS = "{0} ({1})"
    }

    init {
        fallback = "Entities:"
        color = "warning"
        title = "Entities:"
        val fields: MutableList<Field> = ArrayList()
        image.entityTotals.forEach { (k: String, v: Map<String, Int>) ->
            val title = asTitleCase(k) + ":"
            val entityLine = v.entries.stream()
                    .sorted(java.util.Map.Entry.comparingByValue { o1, o2 -> o2 - o1 })
                    .map { e: Map.Entry<String?, Int?> -> MessageFormat.format(CHANNEL_ENTITY_DETAILS, e.key, e.value) }
                    .collect(Collectors.joining(", "))
            fields.add(Field(title, entityLine, false))
        }
        this.fields = fields.toTypedArray()
    }
}