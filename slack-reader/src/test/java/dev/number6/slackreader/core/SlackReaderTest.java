package dev.number6.slackreader.core;

import dev.number6.db.adaptor.DynamoDatabaseAdaptor;
import dev.number6.slackreader.SlackReader;
import dev.number6.slackreader.SlackService;
import dev.number6.slackreader.SnsService;
import dev.number6.slackreader.model.WorkspaceMessages;
import dev.number6.slackreader.generate.SlackReaderRDG;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import uk.org.fyodor.generators.RDG;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.mockito.Mockito.*;

public class SlackReaderTest {

    private final LambdaLogger logger = mock(LambdaLogger.class);
    private final SlackService slackService = mock(SlackService.class);
    private final SnsService snsService = mock(SnsService.class);
    private final DynamoDatabaseAdaptor dbService = mock(DynamoDatabaseAdaptor.class);
    private final Clock clock = Clock.fixed(Instant.now().minus(SlackReaderRDG.longVal(10L).next(), ChronoUnit.DAYS), ZoneId.systemDefault());
    private SlackReader testee;

    @BeforeEach
    void setup() {
        var messages = new WorkspaceMessages(LocalDate.now());
        when(slackService.getMessagesOnDate(any(), any())).thenReturn(messages);

        testee = new SlackReader(slackService, snsService, dbService, clock);
    }

    @Test
    void useDateInEventIfPresent() {

        Map<String, Object> event = new HashMap<>();
        event.put(SlackReader.COMPREHENSION_DATE_FIELD_NAME, "2018-12-25");
        event.put("some-other-event", "bibble");

        testee.handle(event, logger);

        verify(slackService).getMessagesOnDate(LocalDate.of(2018, 12, 25), logger);
    }

    @Test
    void useYesterdayIfNoDatePresentInEvent() {
        Map<String, Object> event = new HashMap<>();
        event.put("some-other-event", "bibble");

        testee.handle(event, logger);

        verify(slackService).getMessagesOnDate(LocalDate.now(clock).minusDays(1), logger);
    }

    @Test
    void useYesterdayIfNothingPresentInEvent() {
        testee.handle(new HashMap<>(), logger);

        verify(slackService).getMessagesOnDate(LocalDate.now(clock).minusDays(1), logger);
    }

    @Test
    void sendToSnsWhatComesBackFromSlack() {
        WorkspaceMessages messages = new WorkspaceMessages(LocalDate.now(clock).minusDays(1));

        when(slackService.getMessagesOnDate(LocalDate.now(clock).minusDays(1), logger)).thenReturn(messages);

        testee.handle(Map.of(), logger);

        verify(snsService).broadcastWorkspaceMessagesForActiveChannels(messages);
    }

    @Test
    void saveToDbBeforePostingToSns() {
        WorkspaceMessages messages = mock(WorkspaceMessages.class);

        Set<String> channelNames = RDG.set(RDG.string(20)).next();
        when(messages.getActiveChannelNames()).thenReturn(channelNames);
        when(slackService.getMessagesOnDate(LocalDate.now(clock).minusDays(1), logger)).thenReturn(messages);

        testee.handle(Map.of(), logger);

        InOrder order = inOrder(snsService, dbService);
        order.verify(dbService).createNewSummaryForChannels(eq(channelNames), any());
        order.verify(snsService).broadcastWorkspaceMessagesForActiveChannels(messages);
    }
}