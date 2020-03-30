package dev.number6.sentiment.dagger;

import dev.number6.comprehend.port.ComprehensionPort;
import dev.number6.comprehend.results.PresentableSentimentResults;
import dev.number6.db.port.DatabasePort;
import dev.number6.message.ChannelMessages;
import dev.number6.message.ChannelMessagesToComprehensionResultsFunction;
import dev.number6.message.ComprehensionResultsConsumer;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

@Module
public class FakeComprehensionResultsModule {

    @Provides
    @Singleton
    public ChannelMessagesToComprehensionResultsFunction<PresentableSentimentResults> providesMessageToSentimentResults(ComprehensionPort comprehensionPort) {
        return new ConfigurableResultsFunction();
    }

    @Provides
    @Singleton
    public ComprehensionResultsConsumer<PresentableSentimentResults> providesSentimentResultsConsumer(DatabasePort databasePort) {
        return new RecordingComprehensionResultsConsumer();
    }

    public static class ConfigurableResultsFunction implements ChannelMessagesToComprehensionResultsFunction<PresentableSentimentResults> {

        private PresentableSentimentResults presentableSentimentResults;

        @Override
        public PresentableSentimentResults apply(ChannelMessages channelMessages) {
            return presentableSentimentResults == null ?
                    fail("presentable entity result needs to be configured") :
                    presentableSentimentResults;
        }

        public void setPresentableSentimentResults(PresentableSentimentResults presentableSentimentResults) {
            this.presentableSentimentResults = presentableSentimentResults;
        }
    }

    public static class RecordingComprehensionResultsConsumer implements ComprehensionResultsConsumer<PresentableSentimentResults> {
        private List<PresentableSentimentResults> resultsConsumed = new ArrayList<>();

        public List<PresentableSentimentResults> getResultsConsumed() {
            return resultsConsumed;
        }

        @Override
        public void accept(PresentableSentimentResults presentableSentimentResults) {
            resultsConsumed.add(presentableSentimentResults);
        }
    }
}
