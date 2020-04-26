package dev.number6.entity.dagger

import dagger.Module
import dagger.Provides
import dev.number6.comprehend.results.PresentableEntityResults
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
    fun providesMessageToEntityResults(): ChannelMessagesToComprehensionResultsFunction<PresentableEntityResults> {
        return ConfigurableResultsFunction()
    }

    @Provides
    @Singleton
    fun providesEntityResultsConsumer(): ComprehensionResultsConsumer<PresentableEntityResults> {
        return RecordingComprehensionResultsConsumer()
    }

    class ConfigurableResultsFunction : ChannelMessagesToComprehensionResultsFunction<PresentableEntityResults> {
        private var presentableEntityResults: PresentableEntityResults? = null
        override fun apply(channelMessages: ChannelMessages): PresentableEntityResults {
            return presentableEntityResults ?: Assertions.fail("presentable entity result needs to be configured")
        }

        fun setPresentableEntityResults(presentableEntityResults: PresentableEntityResults?) {
            this.presentableEntityResults = presentableEntityResults
        }
    }

    class RecordingComprehensionResultsConsumer : ComprehensionResultsConsumer<PresentableEntityResults> {
        private val resultsConsumed: MutableList<PresentableEntityResults> = ArrayList()
        fun getResultsConsumed(): MutableList<PresentableEntityResults> {
            return resultsConsumed
        }

        override fun accept(presentableEntityResults: PresentableEntityResults) {
            resultsConsumed.add(presentableEntityResults)
        }
    }
}