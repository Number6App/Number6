package dev.number6.entity.dagger;

import dev.number6.comprehend.port.ComprehensionPort;
import dev.number6.comprehend.results.PresentableEntityResults;
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
    public ChannelMessagesToComprehensionResultsFunction<PresentableEntityResults> providesMessageToEntityResults(ComprehensionPort comprehensionPort) {
        return new ConfigurableResultsFunction();
    }

    @Provides
    @Singleton
    public ComprehensionResultsConsumer<PresentableEntityResults> providesEntityResultsConsumer(DatabasePort databasePort) {
        return new RecordingComprehensionResultsConsumer();
    }

    public static class ConfigurableResultsFunction implements ChannelMessagesToComprehensionResultsFunction<PresentableEntityResults> {

        private PresentableEntityResults presentableEntityResults;

        @Override
        public PresentableEntityResults apply(ChannelMessages channelMessages) {
            return presentableEntityResults == null ?
                    fail("presentable entity result needs to be configured") :
                    presentableEntityResults;
        }

        public void setPresentableEntityResults(PresentableEntityResults presentableEntityResults) {
            this.presentableEntityResults = presentableEntityResults;
        }
    }

    public static class RecordingComprehensionResultsConsumer implements ComprehensionResultsConsumer<PresentableEntityResults> {
        private List<PresentableEntityResults> resultsConsumed = new ArrayList<>();

        public List<PresentableEntityResults> getResultsConsumed() {
            return resultsConsumed;
        }

        @Override
        public void accept(PresentableEntityResults presentableEntityResults) {
            resultsConsumed.add(presentableEntityResults);
        }
    }
}
