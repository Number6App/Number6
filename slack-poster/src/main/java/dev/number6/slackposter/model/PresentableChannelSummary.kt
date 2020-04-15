package dev.number6.slackposter.model

import dev.number6.slackposter.ChannelSummaryImage
import java.text.MessageFormat

class PresentableChannelSummary(private val image: ChannelSummaryImage) {
    val initialMessageLine: String
        get() = MessageFormat.format(CHANNEL_MESSAGE_TOTAL_FORMAT,
                image.channelName,
                image.sentimentTotals.values.stream().reduce(0) { a: Int, b: Int -> Integer.sum(a, b) },
                image.comprehensionDate)

    val attachments: Array<Attachment>
        get() = arrayOf(overallSentimentAttachment, entityAttachment, keyPhrasesAttachment)

    private val overallSentimentAttachment: Attachment
        get() = SentimentAttachment(image)

    private val keyPhrasesAttachment: Attachment
        get() = KeyPhrasesAttachment(image)

    private val entityAttachment: Attachment
        get() = EntityAttachment(image)

    companion object {
        //\u2022 == bullet point character
        const val CHANNEL_MESSAGE_TOTAL_FORMAT = "\u2022 *{0}*: {1} messages on {2}"
    }

}