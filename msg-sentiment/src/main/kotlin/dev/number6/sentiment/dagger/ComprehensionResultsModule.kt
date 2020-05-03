package dev.number6.sentiment.dagger

import dagger.Module
import dagger.Provides
import dev.number6.comprehend.port.ComprehensionPort
import dev.number6.comprehend.results.PresentableSentimentResults
import dev.number6.db.port.FullDatabasePort
import dev.number6.message.ChannelMessages
import dev.number6.message.ChannelMessagesToComprehensionResultsFunction
import dev.number6.message.ComprehensionResultsConsumer

@Module
class ComprehensionResultsModule {
    @Provides
    fun providesMessageToEntityResults(comprehensionPort: ComprehensionPort): ChannelMessagesToComprehensionResultsFunction<PresentableSentimentResults> {
        return object : ChannelMessagesToComprehensionResultsFunction<PresentableSentimentResults> {
            override fun apply(channelMessages: ChannelMessages): PresentableSentimentResults {
                return comprehensionPort.getSentimentForSlackMessages(channelMessages)
            }
        }
    }

    @Provides
    fun providesEntityResultsConsumer(databasePort: FullDatabasePort): ComprehensionResultsConsumer<PresentableSentimentResults> {
        return object : ComprehensionResultsConsumer<PresentableSentimentResults> {
            override fun accept(results: PresentableSentimentResults) {
                return databasePort.save(results)
            }
        }
    }
}