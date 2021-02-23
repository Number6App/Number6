package dev.number6.entity.dagger

import dagger.Module
import dagger.Provides
import dev.number6.comprehend.port.ComprehensionPort
import dev.number6.comprehend.results.PresentableEntityResults
import dev.number6.db.port.FullDatabasePort
import dev.number6.message.ChannelMessages
import dev.number6.message.ChannelMessagesToComprehensionResultsFunction
import dev.number6.message.ComprehensionResultsConsumer

@Module
class ComprehensionResultsModule {
    @Provides
    fun providesMessageToEntityResults(comprehensionPort: ComprehensionPort): ChannelMessagesToComprehensionResultsFunction<PresentableEntityResults> {
        return object : ChannelMessagesToComprehensionResultsFunction<PresentableEntityResults> {
            override fun apply(channelMessages: ChannelMessages): PresentableEntityResults {
                return comprehensionPort.getEntitiesForSlackMessages(channelMessages)
            }
        }
    }

    @Provides
    fun providesEntityResultsConsumer(databasePort: FullDatabasePort): ComprehensionResultsConsumer<PresentableEntityResults> {
        return object : ComprehensionResultsConsumer<PresentableEntityResults> {
            override fun accept(results: PresentableEntityResults) {
                return databasePort.save(results)
            }
        }
    }
}