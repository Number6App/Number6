package dev.number6.keyphrases.dagger

import dagger.Module
import dagger.Provides
import dev.number6.comprehend.results.PresentableKeyPhrasesResults
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
    fun providesMessageToEntityResults(): ChannelMessagesToComprehensionResultsFunction<PresentableKeyPhrasesResults> {
        return ConfigurableResultsFunction()
    }

    @Provides
    @Singleton
    fun providesEntityResultsConsumer(): ComprehensionResultsConsumer<PresentableKeyPhrasesResults> {
        return RecordingComprehensionResultsConsumer()
    }

    class ConfigurableResultsFunction : ChannelMessagesToComprehensionResultsFunction<PresentableKeyPhrasesResults> {
        private var presentableKeyPhrasesResults: PresentableKeyPhrasesResults? = null
        override fun apply(channelMessages: ChannelMessages): PresentableKeyPhrasesResults {
            return presentableKeyPhrasesResults
                    ?: Assertions.fail("presentable key phrases results needs to be configured")
        }

        fun setPresentableKeyPhrasesResults(presentableKeyPhrasesResults: PresentableKeyPhrasesResults?) {
            this.presentableKeyPhrasesResults = presentableKeyPhrasesResults
        }
    }

    class RecordingComprehensionResultsConsumer : ComprehensionResultsConsumer<PresentableKeyPhrasesResults> {
        private val resultsConsumed: MutableList<PresentableKeyPhrasesResults> = ArrayList()
        fun getResultsConsumed(): MutableList<PresentableKeyPhrasesResults> {
            return resultsConsumed
        }

        override fun accept(presentableKeyPhrasesResults: PresentableKeyPhrasesResults) {
            resultsConsumed.add(presentableKeyPhrasesResults)
        }
    }
}