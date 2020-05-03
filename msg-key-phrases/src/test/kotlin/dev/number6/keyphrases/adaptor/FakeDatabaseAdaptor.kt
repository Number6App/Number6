package dev.number6.keyphrases.adaptor

import dev.number6.comprehend.results.PresentableEntityResults
import dev.number6.comprehend.results.PresentableKeyPhrasesResults
import dev.number6.comprehend.results.PresentableSentimentResults
import dev.number6.db.port.BasicDatabasePort
import dev.number6.db.port.FullDatabasePort
import java.time.LocalDate

class FakeDatabaseAdaptor : FullDatabasePort {
    override fun createNewSummaryForChannels(channelNames: Collection<String>, comprehensionDate: LocalDate) {}
    override fun save(results: PresentableSentimentResults) {}
    override fun save(results: PresentableEntityResults) {}
    override fun save(results: PresentableKeyPhrasesResults) {}
}