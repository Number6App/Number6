package dev.number6.sentiment.dagger

import dagger.Module
import dagger.Provides
import dev.number6.comprehend.port.ComprehensionPort
import dev.number6.comprehend.results.PresentableSentimentResults
import dev.number6.db.port.DatabasePort
import dev.number6.message.ChannelMessages
import dev.number6.message.ChannelMessagesToComprehensionResultsFunction
import dev.number6.message.ComprehensionResultsConsumer
import org.junit.jupiter.api.Assertions
import java.util.*
import javax.inject.Singleton

@Module
class FakeComprehensionResultsModule {
    @Provides
    @Singleton
    fun providesMessageToSentimentResults(): ChannelMessagesToComprehensionResultsFunction<PresentableSentimentResults> {
        return ConfigurableResultsFunction()
    }

    @Provides
    @Singleton
    fun providesSentimentResultsConsumer(): ComprehensionResultsConsumer<PresentableSentimentResults> {
        return RecordingComprehensionResultsConsumer()
    }

    class ConfigurableResultsFunction : ChannelMessagesToComprehensionResultsFunction<PresentableSentimentResults> {
        private var presentableSentimentResults: PresentableSentimentResults? = null
        override fun apply(channelMessages: ChannelMessages): PresentableSentimentResults {
            return presentableSentimentResults ?: Assertions.fail("presentable entity result needs to be configured")
        }

        fun setPresentableSentimentResults(presentableSentimentResults: PresentableSentimentResults) {
            this.presentableSentimentResults = presentableSentimentResults
        }
    }

    class RecordingComprehensionResultsConsumer : ComprehensionResultsConsumer<PresentableSentimentResults> {
        private val resultsConsumed: MutableList<PresentableSentimentResults> = ArrayList()
        fun getResultsConsumed(): MutableList<PresentableSentimentResults> {
            return resultsConsumed
        }

        override fun accept(presentableSentimentResults: PresentableSentimentResults) {
            resultsConsumed.add(presentableSentimentResults)
        }
    }
}