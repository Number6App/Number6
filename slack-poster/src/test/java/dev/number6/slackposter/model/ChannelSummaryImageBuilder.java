package dev.number6.slackposter.model;

import dev.number6.slackposter.ChannelSummaryImage;
import dev.number6.slackposter.SlackPosterRDG;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import uk.org.fyodor.generators.characters.CharacterSetFilter;
import uk.org.fyodor.range.Range;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static dev.number6.slackposter.ChannelSummaryImage.*;

public class ChannelSummaryImageBuilder {

    int version = SlackPosterRDG.integer(8).next();
    String channelName = SlackPosterRDG.string(10, CharacterSetFilter.LettersOnly).next();
    String comprehensionDate = LocalDate.now().toString();
    Map<String, AttributeValue> sentimentTotals = SlackPosterRDG.map(SlackPosterRDG.string(10), SlackPosterRDG.integerAttributeValues(10), Range.closed(5, 20)).next();
    Map<String, AttributeValue> sentimentScoreTotals = SlackPosterRDG.map(SlackPosterRDG.string(10), SlackPosterRDG.doubleAttributeValues(10d), Range.closed(5, 20)).next();
    Map<String, AttributeValue> entityTotals = SlackPosterRDG.map(SlackPosterRDG.string(10, CharacterSetFilter.LettersAndDigits),
            SlackPosterRDG.mapAttributeValues(SlackPosterRDG.string(20, CharacterSetFilter.LettersAndDigits), SlackPosterRDG.integerAttributeValues(10), SlackPosterRDG.integer(20).next()), Range.closed(5, 20)).next();
    Map<String, AttributeValue> keyPhrasesTotals = SlackPosterRDG.map(SlackPosterRDG.string(10), SlackPosterRDG.integerAttributeValues(10), Range.closed(5, 20)).next();

    private ChannelSummaryImageBuilder() {
    }

    public static ChannelSummaryImageBuilder builder() {
        return new ChannelSummaryImageBuilder();
    }

    public static ChannelSummaryImageBuilder finalImage() {
        return new ChannelSummaryImageBuilder().version(FINAL_UPDATE_MINIMUM_VERSION);
    }

    public static ChannelSummaryImageBuilder notFinalImage() {
        return new ChannelSummaryImageBuilder().version(FINAL_UPDATE_MINIMUM_VERSION - 1);
    }

    ChannelSummaryImageBuilder version(int v) {
        this.version = v;
        return this;
    }

    ChannelSummaryImageBuilder sentimentScoreTotals(Map<String, AttributeValue> sentimentScoreTotals) {
        this.sentimentScoreTotals = sentimentScoreTotals;
        return this;
    }

    ChannelSummaryImageBuilder sentimentTotals(Map<String, AttributeValue> sentimentTotals) {
        this.sentimentTotals = sentimentTotals;
        return this;
    }

    ChannelSummaryImageBuilder entityTotals(Map<String, AttributeValue> entityTotals) {
        this.entityTotals = entityTotals;
        return this;
    }

    ChannelSummaryImageBuilder keyPhrasesTotals(Map<String, AttributeValue> keyPhrasesTotals) {
        this.keyPhrasesTotals = keyPhrasesTotals;
        return this;
    }

    public ChannelSummaryImage build() {
        Map<String, AttributeValue> vals = new HashMap<>();
        vals.put(CHANNEL_NAME_KEY, new AttributeValue(channelName));
        vals.put(COMPREHENSION_DATE_KEY, new AttributeValue(comprehensionDate));
        vals.put(VERSION_KEY, new AttributeValue().withN(String.valueOf(version)));
        vals.put(SENTIMENT_TOTALS_KEY, new AttributeValue().withM(sentimentTotals));
        vals.put(SENTIMENT_SCORE_TOTALS, new AttributeValue().withM(sentimentScoreTotals));
        vals.put(ENTITY_TOTALS_KEY, new AttributeValue().withM(entityTotals));
        vals.put(KEYPHRASES_TOTALS_KEY, new AttributeValue().withM(keyPhrasesTotals));
        return new ChannelSummaryImage(vals);
    }
}
