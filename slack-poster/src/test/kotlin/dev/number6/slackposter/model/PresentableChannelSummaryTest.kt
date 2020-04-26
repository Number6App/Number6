package dev.number6.slackposter.model

import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.isEqualTo
import assertk.assertions.isTrue
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import dev.number6.slackposter.ChannelSummaryImage
import org.junit.jupiter.api.Test
import java.text.MessageFormat
import java.util.*
import java.util.stream.Collectors

internal class PresentableChannelSummaryTest {
    @Test
    fun createFirstLine() {
        val sentimentTotals = mapOf("a" to AttributeValue().withN("1"),
                "b" to AttributeValue().withN("2"),
                "c" to AttributeValue().withN("3"))

        val image: ChannelSummaryImage = ChannelSummaryImageBuilder.finalImage()
                .sentimentTotals(sentimentTotals)
                .build()
        val testee = PresentableChannelSummary(image)
        println(testee.initialMessageLine)
        assertThat(testee.initialMessageLine)
                .isEqualTo(MessageFormat.format(PresentableChannelSummary.CHANNEL_MESSAGE_TOTAL_FORMAT,
                        image.channelName, 6, image.comprehensionDate))
    }

    @Test
    fun oneAttachmentOfEachExpectedType() {
        val testee = PresentableChannelSummary(ChannelSummaryImageBuilder.builder().build())
        val classes = Arrays.stream(testee.attachments).collect(Collectors.groupingBy { a: Attachment -> a.javaClass })
        assertThat(classes).hasSize(3)
        assertThat(classes.values.stream().allMatch { v -> v.size == 1 }).isTrue()
    }
}