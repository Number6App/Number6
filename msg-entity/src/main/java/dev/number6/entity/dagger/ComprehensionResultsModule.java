package dev.number6.entity.dagger;

import dev.number6.comprehend.port.ComprehensionPort;
import dev.number6.comprehend.results.PresentableEntityResults;
import dev.number6.db.port.DatabasePort;
import dev.number6.message.ChannelMessagesToComprehensionResultsFunction;
import dev.number6.message.ComprehensionResultsConsumer;
import dagger.Module;
import dagger.Provides;

@Module
public class ComprehensionResultsModule {

    @Provides
    public ChannelMessagesToComprehensionResultsFunction<PresentableEntityResults> providesMessageToEntityResults(ComprehensionPort comprehensionPort) {
        return comprehensionPort::getEntitiesForSlackMessages;
    }

    @Provides
    public ComprehensionResultsConsumer<PresentableEntityResults> providesEntityResultsConsumer(DatabasePort databasePort) {
        return databasePort::save;
    }
}
