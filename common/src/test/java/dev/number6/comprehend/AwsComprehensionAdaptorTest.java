package dev.number6.comprehend;

import com.amazonaws.services.comprehend.model.*;
import dev.number6.comprehend.adaptor.AwsComprehensionAdaptor;
import dev.number6.comprehend.results.*;
import dev.number6.generate.CommonRDG;
import dev.number6.message.ChannelMessages;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AwsComprehensionAdaptorTest {

    private static final String MESSAGE_1_TEXT = "message1";
    private static final String MESSAGE_2_TEXT = "message2";
    private static final String MESSAGE_3_TEXT = "message3";
    private static final String MESSAGE_4_TEXT = "message4";
    private static final String MESSAGE_5_TEXT = "message5";
    private static final String MESSAGE_6_TEXT = "message6";
    private static final String COMMERCIAL_ITEM_ENTITY = "Entity1";
    private static final String EVENT_ENTITY_1 = "Entity2";
    private static final String ORGANIZATION_ENTITY = "Entity3";
    private static final String EVENT_ENTITY_2 = "Entity4";
    private static final String KEY_PHRASE_TEXT_1 = "KeyPhraseText1";
    private static final String KEY_PHRASE_TEXT_2 = "KeyPhraseText2";
    private static final String KEY_PHRASE_TEXT_3 = "KeyPhraseText3";
    private final AwsComprehendClient client = mock(AwsComprehendClient.class);
    private final SentimentResultsToMessageSentimentScore sentimentResultsToMessageSentimentScore = mock(SentimentResultsToMessageSentimentScore.class);
    private final SentimentResultsToMessageSentimentTotals sentimentResultsToMessageSentimentTotals = mock(SentimentResultsToMessageSentimentTotals.class);
    private Map<String, DetectEntitiesResult> messageEntityResults;
    private Map<String, DetectKeyPhrasesResult> messageKeyPhrasesResults;
    private Map<String, DetectSentimentResult> messageSentimentResults;
    private AwsComprehensionAdaptor testee;

    @BeforeEach
    void setup() {
        when(client.getMessageEntities(any())).thenAnswer(i -> messageEntityResults.get(i.getArgument(0)));
        when(client.getMessageKeyPhrases(any())).thenAnswer(i -> messageKeyPhrasesResults.get(i.getArgument(0)));
        when(client.getMessageSentiment(any())).thenAnswer(i -> messageSentimentResults.get(i.getArgument(0)));
        testee = new AwsComprehensionAdaptor(client);
    }

    @BeforeEach
    void setMessageSentimentScores() {
        Map<String, Float> sentimentScores = new HashMap<>();
        sentimentScores.put(SentimentType.MIXED.toString(), 1.1f);
        sentimentScores.put(SentimentType.POSITIVE.toString(), 2.2f);
        sentimentScores.put(SentimentType.NEUTRAL.toString(), 3.3f);
        sentimentScores.put(SentimentType.NEGATIVE.toString(), 4.4f);

        Map<String, Integer> sentimentTotals = new HashMap<>();
        sentimentTotals.put(SentimentType.MIXED.toString(), 10);
        sentimentTotals.put(SentimentType.POSITIVE.toString(), 20);
        sentimentTotals.put(SentimentType.NEUTRAL.toString(), 30);
        sentimentTotals.put(SentimentType.NEGATIVE.toString(), 40);

        when(sentimentResultsToMessageSentimentScore.apply(any())).thenReturn(sentimentScores);
        when(sentimentResultsToMessageSentimentTotals.apply(any())).thenReturn(sentimentTotals);
    }

    @BeforeEach
    void setMessageKeyPhrasesResults() {
        messageKeyPhrasesResults = new HashMap<>();
        messageKeyPhrasesResults.put(MESSAGE_1_TEXT, new DetectKeyPhrasesResult().withKeyPhrases(new KeyPhrase().withText(KEY_PHRASE_TEXT_1)));
        messageKeyPhrasesResults.put(MESSAGE_2_TEXT, new DetectKeyPhrasesResult().withKeyPhrases(new KeyPhrase().withText(KEY_PHRASE_TEXT_2),
                new KeyPhrase().withText(KEY_PHRASE_TEXT_1)));
        messageKeyPhrasesResults.put(MESSAGE_3_TEXT, new DetectKeyPhrasesResult().withKeyPhrases(new KeyPhrase().withText(KEY_PHRASE_TEXT_3),
                new KeyPhrase().withText(KEY_PHRASE_TEXT_2),
                new KeyPhrase().withText(KEY_PHRASE_TEXT_1)));
    }

    @BeforeEach
    void setMessageEntityResults() {
        messageEntityResults = new HashMap<>();
        messageEntityResults.put(MESSAGE_1_TEXT, new DetectEntitiesResult().withEntities(new Entity().withText(COMMERCIAL_ITEM_ENTITY).withType(EntityType.COMMERCIAL_ITEM)));
        messageEntityResults.put(MESSAGE_2_TEXT, new DetectEntitiesResult().withEntities(new Entity().withText(COMMERCIAL_ITEM_ENTITY).withType(EntityType.COMMERCIAL_ITEM),
                new Entity().withText(EVENT_ENTITY_1).withType(EntityType.EVENT)));
        messageEntityResults.put(MESSAGE_3_TEXT, new DetectEntitiesResult().withEntities(new Entity().withText(COMMERCIAL_ITEM_ENTITY).withType(EntityType.COMMERCIAL_ITEM),
                new Entity().withText(EVENT_ENTITY_1).withType(EntityType.EVENT),
                new Entity().withText(EVENT_ENTITY_2).withType(EntityType.EVENT),
                new Entity().withText(ORGANIZATION_ENTITY).withType(EntityType.ORGANIZATION)
        ));
    }

    @BeforeEach
    void setMessageSentimentResults() {
        messageSentimentResults = new HashMap<>();
        messageSentimentResults.put(MESSAGE_1_TEXT, new DetectSentimentResult()
                .withSentiment(SentimentType.NEUTRAL)
                .withSentimentScore(new SentimentScore().withMixed(0.1f).withNegative(0.1f).withNeutral(0.1f).withPositive(0.1f)));
        messageSentimentResults.put(MESSAGE_2_TEXT, new DetectSentimentResult()
                .withSentiment(SentimentType.NEUTRAL)
                .withSentimentScore(new SentimentScore().withMixed(0.2f).withNegative(0.1f).withNeutral(0.2f).withPositive(0.2f)));
        messageSentimentResults.put(MESSAGE_3_TEXT, new DetectSentimentResult()
                .withSentiment(SentimentType.NEUTRAL)
                .withSentimentScore(new SentimentScore().withMixed(0.3f).withNegative(0.2f).withNeutral(0.3f).withPositive(0.3f)));
        messageSentimentResults.put(MESSAGE_4_TEXT, new DetectSentimentResult()
                .withSentiment(SentimentType.MIXED)
                .withSentimentScore(new SentimentScore().withMixed(0.4f).withNegative(0.3f).withNeutral(0.3f).withPositive(0.4f)));
        messageSentimentResults.put(MESSAGE_5_TEXT, new DetectSentimentResult()
                .withSentiment(SentimentType.MIXED)
                .withSentimentScore(new SentimentScore().withMixed(0.5f).withNegative(0.5f).withNeutral(0.4f).withPositive(0.5f)));
        messageSentimentResults.put(MESSAGE_6_TEXT, new DetectSentimentResult()
                .withSentiment(SentimentType.POSITIVE)
                .withSentimentScore(new SentimentScore().withMixed(0.5f).withNegative(0.6f).withNeutral(0.6f).withPositive(0.6f)));
    }

    @Test
    void getsEntities() {
        Collection<String> messages = Arrays.asList(MESSAGE_1_TEXT, MESSAGE_2_TEXT, MESSAGE_3_TEXT);
        String channelName = CommonRDG.string(20).next();
        ChannelMessages channelMessages = new ChannelMessages(channelName, messages, LocalDate.now());

        PresentableEntityResults results = testee.getEntitiesForSlackMessages(channelMessages);
        assertThat(results.getChannelName()).isEqualTo(channelName);
        assertThat(results.getComprehensionDate()).isEqualTo(LocalDate.now());
        assertThat(results.getResults()).hasSize(3);
        assertThat(results.getResults().get(EntityType.COMMERCIAL_ITEM.toString())).hasSize(1);
        assertThat(results.getResults().get(EntityType.COMMERCIAL_ITEM.toString()).get(COMMERCIAL_ITEM_ENTITY)).isEqualTo(3);
        assertThat(results.getResults().get(EntityType.EVENT.toString())).hasSize(2);
        assertThat(results.getResults().get(EntityType.EVENT.toString()).get(EVENT_ENTITY_1)).isEqualTo(2);
        assertThat(results.getResults().get(EntityType.EVENT.toString()).get(EVENT_ENTITY_2)).isEqualTo(1);
        assertThat(results.getResults().get(EntityType.ORGANIZATION.toString())).hasSize(1);
        assertThat(results.getResults().get(EntityType.ORGANIZATION.toString()).get(ORGANIZATION_ENTITY)).isEqualTo(1);
    }

    @Test
    void getsKeyPhrases() {
        Collection<String> messages = Arrays.asList(MESSAGE_1_TEXT, MESSAGE_2_TEXT, MESSAGE_3_TEXT);
        String channelName = CommonRDG.string(20).next();
        ChannelMessages channelMessages = new ChannelMessages(channelName, messages, LocalDate.now());

        PresentableKeyPhrasesResults results = testee.getKeyPhrasesForSlackMessages(channelMessages);
        assertThat(results.getChannelName()).isEqualTo(channelName);
        assertThat(results.getComprehensionDate()).isEqualTo(LocalDate.now());
        assertThat(results.getResults()).hasSize(3);
        assertThat(results.getResults().get(KEY_PHRASE_TEXT_1)).isEqualTo(3);
        assertThat(results.getResults().get(KEY_PHRASE_TEXT_2)).isEqualTo(2);
        assertThat(results.getResults().get(KEY_PHRASE_TEXT_3)).isEqualTo(1);
    }

    @Test
    void getsSentiment() {
        Collection<String> messages = Arrays.asList(MESSAGE_1_TEXT, MESSAGE_2_TEXT, MESSAGE_3_TEXT, MESSAGE_4_TEXT, MESSAGE_5_TEXT, MESSAGE_6_TEXT);
        String channelName = CommonRDG.string(20).next();
        ChannelMessages channelMessages = new ChannelMessages(channelName, messages, LocalDate.now());

        PresentableSentimentResults results = testee.getSentimentForSlackMessages(channelMessages);
        assertThat(results.getChannelName()).isEqualTo(channelName);
        assertThat(results.getComprehensionDate()).isEqualTo(LocalDate.now());
        assertThat(results.getResults()).hasSize(6);
        assertThat(results.getSentimentTotals().get(SentimentType.POSITIVE.toString())).isEqualTo(1);
        assertThat(results.getSentimentTotals().get(SentimentType.MIXED.toString())).isEqualTo(2);
        assertThat(results.getSentimentTotals().get(SentimentType.NEUTRAL.toString())).isEqualTo(3);
        assertThat(results.getSentimentTotals().get(SentimentType.NEGATIVE.toString())).isNull();
        assertThat(results.getSentimentScoreTotals().get(SentimentType.POSITIVE.toString())).isEqualTo(2.1f, Offset.offset(0.001f));
        assertThat(results.getSentimentScoreTotals().get(SentimentType.MIXED.toString())).isEqualTo(2.0f, Offset.offset(0.001f));
        assertThat(results.getSentimentScoreTotals().get(SentimentType.NEUTRAL.toString())).isEqualTo(1.9f, Offset.offset(0.001f));
        assertThat(results.getSentimentScoreTotals().get(SentimentType.NEGATIVE.toString())).isEqualTo(1.8f, Offset.offset(0.001f));
    }
}