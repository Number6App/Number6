package dev.number6.db;

import dev.number6.comprehend.results.PresentableEntityResults;
import dev.number6.comprehend.results.PresentableKeyPhrasesResults;
import dev.number6.comprehend.results.PresentableSentimentResults;
import dev.number6.db.model.ChannelComprehensionSummary;
import dev.number6.db.port.DatabasePort;
import dev.number6.generate.CommonRDG;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collection;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

public class DynamoDatabaseAdaptorIntegrationTest {

    private TestDynamoDatabaseAdaptorComponent component = DaggerTestDynamoDatabaseAdaptorComponent.create();

    @Test
    void createChannels() {
        Collection<String> channelNames = CommonRDG.list(CommonRDG.string()).next();
        TestDynamoDatabaseAdaptorComponent.FakeDynamoDBMapper mapper = (TestDynamoDatabaseAdaptorComponent.FakeDynamoDBMapper) component.getFakeDynamoDBMapper();

        component.getDatabase().createNewSummaryForChannels(channelNames, LocalDate.now());
        assertThat(mapper.getSaved()).hasSize(channelNames.size())
                .extracting("channelName")
                .containsExactlyInAnyOrder(channelNames.toArray());
    }

    @Test
    void saveEntityResults() {
        PresentableEntityResults results = CommonRDG.presentableEntityResults().next();

        ChannelComprehensionSummary summary = saveResults(m -> m.save(results));
        assertThat(summary.getEntityTotals()).isEqualTo(results.getResults());
    }

    @Test
    void saveSentimentResults() {
        PresentableSentimentResults results = CommonRDG.presentableSentimentResults().next();

        ChannelComprehensionSummary summary = saveResults(m -> m.save(results));
        assertThat(summary.getSentimentTotals()).isEqualTo(results.getSentimentTotals());
        assertThat(summary.getSentimentScoreTotals()).isEqualTo(results.getSentimentScoreTotals());
    }

    @Test
    void saveKeyPhrasesResults() {
        PresentableKeyPhrasesResults results = CommonRDG.presentableKeyPhrasesResults().next();

        ChannelComprehensionSummary summary = saveResults(m -> m.save(results));
        assertThat(summary.getKeyPhrasesTotals()).isEqualTo(results.getResults());
    }

    private ChannelComprehensionSummary saveResults(Consumer<DatabasePort> resultsConsumer) {
        TestDynamoDatabaseAdaptorComponent.FakeDynamoDBMapper mapper = (TestDynamoDatabaseAdaptorComponent.FakeDynamoDBMapper) component.getFakeDynamoDBMapper();
        resultsConsumer.accept(component.getDatabase());
        assertThat(mapper.getSaved()).hasSize(1);
        return mapper.getSaved().get(0);
    }
}
