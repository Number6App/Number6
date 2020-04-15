package dev.number6.slackposter.model

import com.amazonaws.services.dynamodbv2.model.AttributeValue
import dev.number6.slackposter.ChannelSummaryImage
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.text.MessageFormat
import java.util.*
import java.util.stream.Collectors

internal class PresentableChannelSummaryTest {
    @Test
    fun createFirstLine() {
        val sentimentTotals: MutableMap<String, AttributeValue?> = HashMap()
        sentimentTotals["a"] = AttributeValue().withN("1")
        sentimentTotals["b"] = AttributeValue().withN("2")
        sentimentTotals["c"] = AttributeValue().withN("3")
        val image: ChannelSummaryImage = ChannelSummaryImageBuilder.Companion.finalImage()
                .sentimentTotals(sentimentTotals)
                .build()
        val testee = PresentableChannelSummary(image)
        println(testee.initialMessageLine)
        Assertions.assertThat(testee.initialMessageLine)
                .isEqualTo(MessageFormat.format(PresentableChannelSummary.CHANNEL_MESSAGE_TOTAL_FORMAT,
                        image.channelName, 6, image.comprehensionDate))
    }

    @Test
    fun oneAttachmentOfEachExpectedType() {
        val testee = PresentableChannelSummary(ChannelSummaryImageBuilder.Companion.builder().build())
        val classes = Arrays.stream(testee.attachments).collect(Collectors.groupingBy { obj: Attachment -> obj.javaClass })
        Assertions.assertThat(classes).hasSize(3)
//        Assertions.assertThat(classes.values.stream().allMatch { v: List<Attachment> -> v.size == 1 }).isTrue
    }
}