package dev.number6.keyphrases.dagger

import dagger.Module
import dagger.Provides
import dev.number6.comprehend.port.ComprehensionPort
import dev.number6.comprehend.results.PresentableKeyPhrasesResults
import dev.number6.db.port.FullDatabasePort
import dev.number6.message.ChannelMessages
import dev.number6.message.ChannelMessagesToComprehensionResultsFunction
import dev.number6.message.ComprehensionResultsConsumer

@Module
class ComprehensionResultsModule {
    @Provides
    fun providesMessageToKeyPhrasesResults(comprehensionPort: ComprehensionPort): ChannelMessagesToComprehensionResultsFunction<PresentableKeyPhrasesResults> {
        return object : ChannelMessagesToComprehensionResultsFunction<PresentableKeyPhrasesResults> {
            override fun apply(channelMessages: ChannelMessages): PresentableKeyPhrasesResults {
                return comprehensionPort.getKeyPhrasesForSlackMessages(channelMessages)
            }
        }
    }

    @Provides
    fun providesKeyPhrasesResultsConsumer(databasePort: FullDatabasePort): ComprehensionResultsConsumer<PresentableKeyPhrasesResults> {
        return object : ComprehensionResultsConsumer<PresentableKeyPhrasesResults> {
            override fun accept(results: PresentableKeyPhrasesResults) {
                return databasePort.save(results)
            }
        }
    }
}