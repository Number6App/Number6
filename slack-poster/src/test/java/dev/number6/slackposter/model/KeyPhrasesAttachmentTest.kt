package dev.number6.slackposter.model

import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import dev.number6.slackposter.ChannelSummaryImage
import org.junit.jupiter.api.Test

internal class KeyPhrasesAttachmentTest {
    @Test
    fun createsFieldsWithExpectedValues() {
        val expectedValue = "KeyPhrase3 (11), KeyPhrase4 (10), KeyPhrase2 (2)"
        val keyPhrasesTotals = mapOf("KeyPhrase1" to AttributeValue().withN("1"),
                "KeyPhrase2" to AttributeValue().withN("2"),
                "KeyPhrase3" to AttributeValue().withN("11"),
                "KeyPhrase4" to AttributeValue().withN("10"))

        val image: ChannelSummaryImage = ChannelSummaryImageBuilder.builder()
                .keyPhrasesTotals(keyPhrasesTotals)
                .build()
        val testee = KeyPhrasesAttachment.fromImage(image)

        assertThat(testee.fields).hasSize(1)
        assertThat(testee.fields[0].value).isEqualTo(expectedValue)
        assertThat(testee.fields[0].title).isEmpty()
        assertThat(testee.fields[0].shortField).isFalse()
    }
}