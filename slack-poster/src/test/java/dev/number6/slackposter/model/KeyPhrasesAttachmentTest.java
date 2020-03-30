package dev.number6.slackposter.model;

import dev.number6.slackposter.ChannelSummaryImage;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class KeyPhrasesAttachmentTest {

    @Test
    void createsFieldsWithExpectedValues() {
        String expectedValue = "KeyPhrase3 (11), KeyPhrase4 (10), KeyPhrase2 (2)";
        Map<String, AttributeValue> keyPhrasesTotals = new HashMap<>();
        keyPhrasesTotals.put("KeyPhrase1", new AttributeValue().withN("1"));
        keyPhrasesTotals.put("KeyPhrase2", new AttributeValue().withN("2"));
        keyPhrasesTotals.put("KeyPhrase3", new AttributeValue().withN("11"));
        keyPhrasesTotals.put("KeyPhrase4", new AttributeValue().withN("10"));

        ChannelSummaryImage image = ChannelSummaryImageBuilder.builder()
                .keyPhrasesTotals(keyPhrasesTotals)
                .build();
        KeyPhrasesAttachment testee = new KeyPhrasesAttachment(image);
        System.out.println(Arrays.toString(testee.fields));
        Assertions.assertThat(testee.fields).hasSize(1);
        assertThat(testee.fields[0].getValue()).isEqualTo(expectedValue);
        assertThat(testee.fields[0].getTitle()).isEmpty();
        assertThat(testee.fields[0].getShortField()).isFalse();
    }

}