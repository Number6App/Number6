package dev.number6.slackreader.core;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.google.gson.Gson;
import dev.number6.db.model.ChannelComprehensionSummary;
import dev.number6.message.ChannelMessages;
import dev.number6.slackreader.SlackReader;
import dev.number6.slackreader.dagger.*;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class SlackReaderIntegrationTest {

    private final Gson gson = new Gson();
    private final LambdaLogger logger = new LambdaLogger() {
        @Override
        public void log(String message) {
            System.out.println(message);
        }

        @Override
        public void log(byte[] message) {
            System.out.println(Arrays.toString(message));
        }
    };

    private final TestSlackReaderComponent testee = DaggerTestSlackReaderComponent.create();

    @Test
    void handleEventWithoutDate() {

        Clock clock = testee.getClock();
        Map<String, Object> triggerEvent = new HashMap<>();
        testEventComprehensionDate(triggerEvent, LocalDate.now(clock));
    }

    @Test
    void handleEventWithDate() {
        Clock clock = testee.getClock();
        Map<String, Object> triggerEvent = new HashMap<>();
        triggerEvent.put(SlackReader.COMPREHENSION_DATE_FIELD_NAME, LocalDate.now(clock).toString());
        testEventComprehensionDate(triggerEvent, LocalDate.now(clock));
    }

    private void testEventComprehensionDate(Map<String, Object> triggerEvent, LocalDate comprehensionDate) {

        RecordingSlackReaderAdaptor reader = (RecordingSlackReaderAdaptor) testee.getSlackPort();
        FakeDynamoDbMapperModule.FakeAmazonDynamoDB fakeDynamo = testee.getFakeAmazonDynamoClient();
        FakeAmazonSnsModule.FakeAmazonSns fakeSns = testee.getFakeAmazonSns();

        if (triggerEvent == null) {
            triggerEvent = new HashMap<>();
        } else {
            triggerEvent.put(SlackReader.COMPREHENSION_DATE_FIELD_NAME, comprehensionDate.toString());
        }
        testee.handler().handle(triggerEvent, logger);

        List<ChannelComprehensionSummary> savedChannelSummaries = fakeDynamo.getSavedObjects().stream()
                .map(o -> ((ChannelComprehensionSummary) o))
                .collect(Collectors.toList());

        savedChannelSummaries.forEach(s -> assertThat(s.getComprehensionDate()).isEqualTo(comprehensionDate));
        assertThat(savedChannelSummaries.stream().map(ChannelComprehensionSummary::getChannelName).collect(Collectors.toList()))
                .containsExactlyInAnyOrderElementsOf(reader.getChannelNames());

        Collection<ChannelMessages> channelMessages = fakeSns.getPublishedMessages().stream()
                .map(p -> gson.fromJson(p, ChannelMessages.class))
                .collect(Collectors.toList());

        channelMessages.forEach(cm -> assertThat(cm.getChannelName()).isIn(reader.getChannelNames()));
        channelMessages.forEach(cm -> assertThat(cm.getMessages()).containsExactlyInAnyOrderElementsOf(reader.getMessagesForChannelName(cm.getChannelName())));
    }
}
