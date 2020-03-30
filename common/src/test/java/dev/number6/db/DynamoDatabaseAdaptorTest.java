package dev.number6.db;

import dev.number6.comprehend.results.PresentableEntityResults;
import dev.number6.comprehend.results.PresentableSentimentResults;
import dev.number6.comprehend.results.SentimentResultsToMessageSentimentScore;
import dev.number6.comprehend.results.SentimentResultsToMessageSentimentTotals;
import dev.number6.db.adaptor.DatabaseServiceConfigurationAdaptor;
import dev.number6.db.adaptor.DynamoDatabaseAdaptor;
import dev.number6.db.model.ChannelComprehensionSummary;
import dev.number6.db.port.DatabaseConfigurationPort;
import dev.number6.generate.CommonRDG;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import uk.org.fyodor.generators.RDG;
import uk.org.fyodor.range.Range;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class DynamoDatabaseAdaptorTest {

    private static final String OVERRIDE_TABLE_NAME = "OverrideTableName";
    private final DynamoDBMapper mapper = mock(DynamoDBMapper.class);
    private final DynamoDBMapperConfig dynamoDBMapperConfig = mock(DynamoDBMapperConfig.class);
    private final DatabaseConfigurationPort dbConfig = mock(DatabaseServiceConfigurationAdaptor.class);
    private DynamoDatabaseAdaptor testee;

    @BeforeEach
    void setup() {
        DynamoDBMapperConfig.TableNameOverride tableNameOverride = new DynamoDBMapperConfig.TableNameOverride(OVERRIDE_TABLE_NAME);
        when(dynamoDBMapperConfig.getTableNameOverride()).thenReturn(tableNameOverride);
        when(dbConfig.getDynamoDBMapperConfig()).thenReturn(dynamoDBMapperConfig);
        testee = new DynamoDatabaseAdaptor(mapper, dbConfig);
    }

    @Test
    void savesComprehensionSummary() {
        List<String> channelNames = RDG.list(RDG.string(20), 1).next();
        LocalDate comprehensionDate = LocalDate.now();

        ArgumentCaptor<DynamoDBMapperConfig> configCaptor = ArgumentCaptor.forClass(DynamoDBMapperConfig.class);
        ArgumentCaptor<ChannelComprehensionSummary> summaryCaptor = ArgumentCaptor.forClass(ChannelComprehensionSummary.class);
        testee.createNewSummaryForChannels(channelNames, comprehensionDate);

        verify(mapper).save(summaryCaptor.capture(), configCaptor.capture());
        assertThat(summaryCaptor.getValue().getChannelName()).isEqualTo(channelNames.get(0));
        assertThat(summaryCaptor.getValue().getComprehensionDate()).isEqualTo(comprehensionDate);
        assertThat(configCaptor.getValue().getTableNameOverride().getTableName()).isEqualTo(OVERRIDE_TABLE_NAME);
    }

    @Test
    void savesComprehensionSummaryForEachChannel() {
        Collection<String> channelNames = RDG.list(RDG.string(), Range.closed(3, 20)).next();
        LocalDate comprehensionDate = LocalDate.now();

        ArgumentCaptor<DynamoDBMapperConfig> configCaptor = ArgumentCaptor.forClass(DynamoDBMapperConfig.class);
        ArgumentCaptor<ChannelComprehensionSummary> summaryCaptor = ArgumentCaptor.forClass(ChannelComprehensionSummary.class);
        testee.createNewSummaryForChannels(channelNames, comprehensionDate);

        verify(mapper, times(channelNames.size())).save(summaryCaptor.capture(), configCaptor.capture());

        assertThat(summaryCaptor.getAllValues().stream().map(ChannelComprehensionSummary::getChannelName).collect(Collectors.toList())).containsExactlyInAnyOrderElementsOf(channelNames);
        summaryCaptor.getAllValues().forEach(c -> assertThat(c.getComprehensionDate()).isEqualTo(comprehensionDate));
        configCaptor.getAllValues().forEach(c -> assertThat(c.getTableNameOverride().getTableName()).isEqualTo(OVERRIDE_TABLE_NAME));
    }

    @Test
    void saveEntityResults() {

        String channelName = RDG.string(20).next();
        ChannelComprehensionSummary summary = new ChannelComprehensionSummary(channelName, LocalDate.now());
        when(mapper.load(any(), any(), any(), any())).thenReturn(summary);
        Map<String, Map<String, Long>> detectEntitiesResults = CommonRDG.map(CommonRDG.string(), CommonRDG.map(CommonRDG.string(20), CommonRDG.longVal(Range.closed(1L, 10L)))).next();
        PresentableEntityResults results = new PresentableEntityResults(LocalDate.now(), detectEntitiesResults, channelName);

        ArgumentCaptor<DynamoDBMapperConfig> configCaptor = ArgumentCaptor.forClass(DynamoDBMapperConfig.class);
        ArgumentCaptor<ChannelComprehensionSummary> summaryCaptor = ArgumentCaptor.forClass(ChannelComprehensionSummary.class);
        testee.save(results);

        verify(mapper).save(summaryCaptor.capture(), configCaptor.capture());
        assertThat(configCaptor.getValue().getTableNameOverride().getTableName()).isEqualTo(OVERRIDE_TABLE_NAME);
        assertThat(summaryCaptor.getValue().getChannelName()).isEqualTo(channelName);
        assertThat(summaryCaptor.getValue().getComprehensionDate()).isEqualTo(LocalDate.now());
        assertThat(summaryCaptor.getValue().getEntityTotals()).isEqualTo(detectEntitiesResults);
    }

    @Test
    public void saveSentimentResults() {
        String channelName = RDG.string(20).next();
        Map<String, Float> sentimentScores = new HashMap<>();
        sentimentScores.put("Sentiment", 0.3f);
        Map<String, Integer> sentimentTotals = new HashMap<>();
        sentimentTotals.put("Entity", 2);
        ChannelComprehensionSummary summary = new ChannelComprehensionSummary(channelName, LocalDate.now());
        when(mapper.load(any(), any(), any(), any())).thenReturn(summary);
        ArgumentCaptor<DynamoDBMapperConfig> configCaptor = ArgumentCaptor.forClass(DynamoDBMapperConfig.class);
        ArgumentCaptor<ChannelComprehensionSummary> summaryCaptor = ArgumentCaptor.forClass(ChannelComprehensionSummary.class);

        SentimentResultsToMessageSentimentScore sentimentResultsToMessageSentimentScore = mock(SentimentResultsToMessageSentimentScore.class);
        SentimentResultsToMessageSentimentTotals sentimentResultsToMessageSentimentTotals = mock(SentimentResultsToMessageSentimentTotals.class);
        when(sentimentResultsToMessageSentimentScore.apply(any())).thenReturn(sentimentScores);
        when(sentimentResultsToMessageSentimentTotals.apply(any())).thenReturn(sentimentTotals);
        PresentableSentimentResults sentimentResults = new PresentableSentimentResults(LocalDate.now(),
                new ArrayList<>(),
                channelName,
                sentimentResultsToMessageSentimentScore,
                sentimentResultsToMessageSentimentTotals);

        testee.save(sentimentResults);
        verify(mapper).save(summaryCaptor.capture(), configCaptor.capture());

        assertThat(configCaptor.getValue().getTableNameOverride().getTableName()).isEqualTo(OVERRIDE_TABLE_NAME);
        assertThat(summaryCaptor.getValue().getChannelName()).isEqualTo(channelName);
        assertThat(summaryCaptor.getValue().getComprehensionDate()).isEqualTo(LocalDate.now());
        assertThat(summaryCaptor.getValue().getSentimentScoreTotals()).isEqualTo(sentimentScores);
        assertThat(summaryCaptor.getValue().getSentimentTotals()).isEqualTo(sentimentTotals);
    }
}