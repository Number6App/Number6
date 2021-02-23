package dev.number6.generate

import uk.org.fyodor.generators.RDG

object ComprehendRDG : RDG() {
    private val presentableEntityResultsGenerator = PresentableEntityResultsGenerator()
    private val presentableKeyPhrasesResultsGenerator = PresentableKeyPhrasesResultsGenerator()
    private val presentableSentimentResultsGenerator = PresentableSentimentResultsGenerator()
    private val channelMessagesGenerator = ChannelMessagesGenerator()
    private val channelComprehensionSummaryGenerator = ChannelComprehensionSummaryGenerator()
    private val entityGenerator = EntityGenerator()
    private val keyPhraseGenerator = KeyPhraseGenerator()

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

    fun detectEntitiesResult(): DetectEntitiesResultGenerator {
        return DetectEntitiesResultGenerator()
    }

    fun detectKeyPhrasesResult(): DetectKeyPhrasesResultGenerator {
        return DetectKeyPhrasesResultGenerator()
    }

    fun keyPhrase(): KeyPhraseGenerator {
        return keyPhraseGenerator
    }

    fun entity() : EntityGenerator {
        return entityGenerator
    }

    fun channelMessages(): ChannelMessagesGenerator {
        return channelMessagesGenerator
    }

    fun sentimentScoreFloat(): Float {
        return doubleVal(1.0).next().toFloat()
    }
}