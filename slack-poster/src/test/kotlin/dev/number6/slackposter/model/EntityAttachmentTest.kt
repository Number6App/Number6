package dev.number6.slackposter.model

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import dev.number6.slackposter.ChannelSummaryImage
import org.junit.jupiter.api.Test

internal class EntityAttachmentTest {
    @Test
    fun createsFieldsWithExpectedValues() {

        val expectedFieldValues = mapOf("Entity1:" to "Entity1Object1 (3)",
                "Entity2:" to "Entity2Object2 (4), Entity2Object1 (2)",
                "Entity3:" to "Entity3Object2 (10), Entity3Object3 (2), Entity3Object1 (1)")

        val entityTotals = mapOf("Entity1" to AttributeValue().withM(mapOf("Entity1Object1" to AttributeValue().withN("3"))),
                "Entity2" to AttributeValue().withM(mapOf("Entity2Object1" to AttributeValue().withN("2"),
                        "Entity2Object2" to AttributeValue().withN("4"))),
                "Entity3" to AttributeValue().withM(mapOf("Entity3Object1" to AttributeValue().withN("1"),
                        "Entity3Object2" to AttributeValue().withN("10"),
                        "Entity3Object3" to AttributeValue().withN("2"))))

        val image: ChannelSummaryImage = ChannelSummaryImageBuilder.builder()
                .entityTotals(entityTotals)
                .build()
        val testee = EntityAttachment.fromImage(image)

        testee.fields.forEach { f -> assertThat(f.value).isEqualTo(expectedFieldValues[f.title]) }
    }
}