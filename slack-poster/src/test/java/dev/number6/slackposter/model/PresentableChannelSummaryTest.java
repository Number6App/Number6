package dev.number6.slackposter.model;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import org.junit.jupiter.api.Test;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static dev.number6.slackposter.model.PresentableChannelSummary.CHANNEL_MESSAGE_TOTAL_FORMAT;
import static org.assertj.core.api.Assertions.assertThat;

class PresentableChannelSummaryTest {

    @Test
    void createFirstLine() {
        Map<String, AttributeValue> sentimentTotals = new HashMap<>();
        sentimentTotals.put("a", new AttributeValue().withN("1"));
        sentimentTotals.put("b", new AttributeValue().withN("2"));
        sentimentTotals.put("c", new AttributeValue().withN("3"));
        var image = ChannelSummaryImageBuilder.finalImage()
                .sentimentTotals(sentimentTotals)
                .build();
        var testee = new PresentableChannelSummary(image);

        System.out.println(testee.getInitialMessageLine());
        assertThat(testee.getInitialMessageLine())
                .isEqualTo(MessageFormat.format(CHANNEL_MESSAGE_TOTAL_FORMAT,
                        image.getChannelName(), 6, image.getComprehensionDate()));
    }

    @Test
    void oneAttachmentOfEachExpectedType() {
        var testee = new PresentableChannelSummary(ChannelSummaryImageBuilder.builder().build());
        var classes = Arrays.stream(testee.getAttachments()).collect(Collectors.groupingBy(Attachment::getClass));
        assertThat(classes).hasSize(3);
        assertThat(classes.values().stream().allMatch(v -> v.size() == 1)).isTrue();
    }
}