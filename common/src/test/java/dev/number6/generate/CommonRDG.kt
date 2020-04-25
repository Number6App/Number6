package dev.number6.generate

import uk.org.fyodor.generators.RDG

object CommonRDG : RDG() {
    private val presentableEntityResultsGenerator = PresentableEntityResultsGenerator()
    private val presentableKeyPhrasesResultsGenerator = PresentableKeyPhrasesResultsGenerator()
    private val presentableSentimentResultsGenerator = PresentableSentimentResultsGenerator()
    private val channelMessagesGenerator = ChannelMessagesGenerator()
    private val channelComprehensionSummaryGenerator = ChannelComprehensionSummaryGenerator()
    fun channelComprehensionSummary(): ChannelComprehensionSummaryGenerator {
        return channelComprehensionSummaryGenerator
    }

    fun presentableEntityResults(): PresentableEntityResultsGenerator {
        return presentableEntityResultsGenerator
    }

    fun presentableSentimentResults(): PresentableSentimentResultsGenerator {
        return presentableSentimentResultsGenerator
    }

    fun presentableKeyPhrasesResults(): PresentableKeyPhrasesResultsGenerator {
        return presentableKeyPhrasesResultsGenerator
    }

    fun detectSentimentResult(): DetectSentimentResultGenerator {
        return DetectSentimentResultGenerator()
    }

    fun channelMessages(): ChannelMessagesGenerator {
        return channelMessagesGenerator
    }

    fun sentimentScoreFloat(): Float {
        return doubleVal(1.0).next().toFloat()
    }
}