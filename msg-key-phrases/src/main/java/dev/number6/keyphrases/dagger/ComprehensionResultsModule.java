package dev.number6.keyphrases.dagger;

import dev.number6.comprehend.port.ComprehensionPort;
import dev.number6.comprehend.results.PresentableKeyPhrasesResults;
import dev.number6.db.port.DatabasePort;
import dev.number6.message.ChannelMessagesToComprehensionResultsFunction;
import dev.number6.message.ComprehensionResultsConsumer;
import dagger.Module;
import dagger.Provides;

@Module
public class ComprehensionResultsModule {

    @Provides
    public ChannelMessagesToComprehensionResultsFunction<PresentableKeyPhrasesResults> providesMessageToKeyPhrasesResults(ComprehensionPort comprehensionPort) {
        return comprehensionPort::getKeyPhrasesForSlackMessages;
    }

    @Provides
    public ComprehensionResultsConsumer<PresentableKeyPhrasesResults> providesKeyPhrasesResultsConsumer(DatabasePort databasePort) {
        return databasePort::save;
    }

}
