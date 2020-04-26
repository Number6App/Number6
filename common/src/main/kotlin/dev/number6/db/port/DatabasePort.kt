package dev.number6.db.port

import dev.number6.comprehend.results.PresentableEntityResults
import dev.number6.comprehend.results.PresentableKeyPhrasesResults
import dev.number6.comprehend.results.PresentableSentimentResults
import java.time.LocalDate

interface DatabasePort {
    fun createNewSummaryForChannels(channelNames: Collection<String>, comprehensionDate: LocalDate)
    fun save(results: PresentableSentimentResults)
    fun save(results: PresentableEntityResults)
    fun save(results: PresentableKeyPhrasesResults)
}