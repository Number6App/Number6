package dev.number6.db.port

import dev.number6.comprehend.results.PresentableEntityResults
import dev.number6.comprehend.results.PresentableKeyPhrasesResults
import dev.number6.comprehend.results.PresentableSentimentResults

interface FullDatabasePort : BasicDatabasePort {
    fun save(results: PresentableSentimentResults)
    fun save(results: PresentableEntityResults)
    fun save(results: PresentableKeyPhrasesResults)
}