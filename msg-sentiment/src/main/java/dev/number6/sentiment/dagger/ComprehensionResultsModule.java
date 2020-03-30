package dev.number6.sentiment.dagger;

import dev.number6.comprehend.port.ComprehensionPort;
import dev.number6.comprehend.results.PresentableSentimentResults;
import dev.number6.db.port.DatabasePort;
import dev.number6.message.ChannelMessagesToComprehensionResultsFunction;
import dev.number6.message.ComprehensionResultsConsumer;
import dagger.Module;
import dagger.Provides;

@Module
public class ComprehensionResultsModule {

    @Provides
    public ChannelMessagesToComprehensionResultsFunction<PresentableSentimentResults> providesMessageToEntityResults(ComprehensionPort comprehensionPort) {
        return comprehensionPort::getSentimentForSlackMessages;
    }

    @Provides
    public ComprehensionResultsConsumer<PresentableSentimentResults> providesEntityResultsConsumer(DatabasePort databasePort) {
        return databasePort::save;
    }
}
