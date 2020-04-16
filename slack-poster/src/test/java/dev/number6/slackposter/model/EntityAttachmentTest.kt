package dev.number6.slackposter.model

import com.amazonaws.services.dynamodbv2.model.AttributeValue
import dev.number6.slackposter.ChannelSummaryImage
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.util.*
import java.util.stream.Stream

internal class EntityAttachmentTest {
    @Test
    fun createsFieldsWithExpectedValues() {
        val expectedFieldValues: MutableMap<String?, String> = HashMap()
        expectedFieldValues["Entity1:"] = "Entity1Object1 (3)"
        expectedFieldValues["Entity2:"] = "Entity2Object2 (4), Entity2Object1 (2)"
        expectedFieldValues["Entity3:"] = "Entity3Object2 (10), Entity3Object3 (2), Entity3Object1 (1)"
        val entityTotals: MutableMap<String, AttributeValue?> = HashMap()
        val entity1: MutableMap<String, AttributeValue> = HashMap()
        val entity2: MutableMap<String, AttributeValue> = HashMap()
        val entity3: MutableMap<String, AttributeValue> = HashMap()
        entity1["Entity1Object1"] = AttributeValue().withN("3")
        entity2["Entity2Object1"] = AttributeValue().withN("2")
        entity2["Entity2Object2"] = AttributeValue().withN("4")
        entity3["Entity3Object1"] = AttributeValue().withN("1")
        entity3["Entity3Object2"] = AttributeValue().withN("10")
        entity3["Entity3Object3"] = AttributeValue().withN("2")
        entityTotals["Entity1"] = AttributeValue().withM(entity1)
        entityTotals["Entity2"] = AttributeValue().withM(entity2)
        entityTotals["Entity3"] = AttributeValue().withM(entity3)
        val image: ChannelSummaryImage = ChannelSummaryImageBuilder.Companion.builder()
                .entityTotals(entityTotals)
                .build()
        val testee = EntityAttachment.fromImage(image)
        println(Arrays.toString(testee.fields))
        Stream.of(*testee.fields).forEach { f: Field -> Assertions.assertThat(f.value).isEqualTo(expectedFieldValues[f.title]) }
    }
}