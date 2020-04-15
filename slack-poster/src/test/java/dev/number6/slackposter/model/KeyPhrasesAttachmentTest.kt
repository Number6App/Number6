package dev.number6.slackposter.model

import com.amazonaws.services.dynamodbv2.model.AttributeValue
import dev.number6.slackposter.ChannelSummaryImage
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.util.*

internal class KeyPhrasesAttachmentTest {
    @Test
    fun createsFieldsWithExpectedValues() {
        val expectedValue = "KeyPhrase3 (11), KeyPhrase4 (10), KeyPhrase2 (2)"
        val keyPhrasesTotals: MutableMap<String, AttributeValue?> = HashMap()
        keyPhrasesTotals["KeyPhrase1"] = AttributeValue().withN("1")
        keyPhrasesTotals["KeyPhrase2"] = AttributeValue().withN("2")
        keyPhrasesTotals["KeyPhrase3"] = AttributeValue().withN("11")
        keyPhrasesTotals["KeyPhrase4"] = AttributeValue().withN("10")
        val image: ChannelSummaryImage = ChannelSummaryImageBuilder.Companion.builder()
                .keyPhrasesTotals(keyPhrasesTotals)
                .build()
        val testee = KeyPhrasesAttachment(image)
        println(Arrays.toString(testee.fields))
        Assertions.assertThat(testee.fields).hasSize(1)
        Assertions.assertThat(testee.fields[0].value).isEqualTo(expectedValue)
        Assertions.assertThat(testee.fields[0].title).isEmpty()
//        Assertions.assertThat(testee.fields[0].shortField).isFalse
    }
}