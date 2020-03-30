package dev.number6.keyphrases.dagger;

import dev.number6.comprehend.results.PresentableKeyPhrasesResults;
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
    public ChannelMessagesToComprehensionResultsFunction<PresentableKeyPhrasesResults> providesMessageToEntityResults() {
        return new ConfigurableResultsFunction();
    }

    @Provides
    @Singleton
    public ComprehensionResultsConsumer<PresentableKeyPhrasesResults> providesEntityResultsConsumer() {
        return new RecordingComprehensionResultsConsumer();
    }

    public static class ConfigurableResultsFunction implements ChannelMessagesToComprehensionResultsFunction<PresentableKeyPhrasesResults> {

        private PresentableKeyPhrasesResults presentableKeyPhrasesResults;

        @Override
        public PresentableKeyPhrasesResults apply(ChannelMessages channelMessages) {
            return presentableKeyPhrasesResults == null ?
                    fail("presentable key phrases results needs to be configured") :
                    presentableKeyPhrasesResults;
        }

        public void setPresentableKeyPhrasesResults(PresentableKeyPhrasesResults presentableKeyPhrasesResults) {
            this.presentableKeyPhrasesResults = presentableKeyPhrasesResults;
        }
    }

    public static class RecordingComprehensionResultsConsumer implements ComprehensionResultsConsumer<PresentableKeyPhrasesResults> {
        private List<PresentableKeyPhrasesResults> resultsConsumed = new ArrayList<>();

        public List<PresentableKeyPhrasesResults> getResultsConsumed() {
            return resultsConsumed;
        }

        @Override
        public void accept(PresentableKeyPhrasesResults presentableKeyPhrasesResults) {
            resultsConsumed.add(presentableKeyPhrasesResults);
        }
    }
}
