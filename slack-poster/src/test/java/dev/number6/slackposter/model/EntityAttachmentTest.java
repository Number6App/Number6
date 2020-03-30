package dev.number6.slackposter.model;

import dev.number6.slackposter.ChannelSummaryImage;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class EntityAttachmentTest {

    @Test
    void createsFieldsWithExpectedValues() {
        Map<String, String> expectedFieldValues = new HashMap<>();
        expectedFieldValues.put("Entity1:", "Entity1Object1 (3)");
        expectedFieldValues.put("Entity2:", "Entity2Object2 (4), Entity2Object1 (2)");
        expectedFieldValues.put("Entity3:", "Entity3Object2 (10), Entity3Object3 (2), Entity3Object1 (1)");
        Map<String, AttributeValue> entityTotals = new HashMap<>();
        Map<String, AttributeValue> entity1 = new HashMap<>();
        Map<String, AttributeValue> entity2 = new HashMap<>();
        Map<String, AttributeValue> entity3 = new HashMap<>();
        entity1.put("Entity1Object1", new AttributeValue().withN("3"));
        entity2.put("Entity2Object1", new AttributeValue().withN("2"));
        entity2.put("Entity2Object2", new AttributeValue().withN("4"));
        entity3.put("Entity3Object1", new AttributeValue().withN("1"));
        entity3.put("Entity3Object2", new AttributeValue().withN("10"));
        entity3.put("Entity3Object3", new AttributeValue().withN("2"));
        entityTotals.put("Entity1", new AttributeValue().withM(entity1));
        entityTotals.put("Entity2", new AttributeValue().withM(entity2));
        entityTotals.put("Entity3", new AttributeValue().withM(entity3));


        ChannelSummaryImage image = ChannelSummaryImageBuilder.builder()
                .entityTotals(entityTotals)
                .build();
        EntityAttachment testee = new EntityAttachment(image);
        System.out.println(Arrays.toString(testee.fields));
        Stream.of(testee.fields).forEach(f ->
                assertThat(f.getValue()).isEqualTo(expectedFieldValues.get(f.getTitle())));
    }

}