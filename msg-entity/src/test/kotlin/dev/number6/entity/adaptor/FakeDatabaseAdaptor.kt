package dev.number6.entity.adaptor

import dev.number6.comprehend.results.PresentableEntityResults
import dev.number6.comprehend.results.PresentableKeyPhrasesResults
import dev.number6.comprehend.results.PresentableSentimentResults
import dev.number6.db.port.DatabasePort
import java.time.LocalDate

class FakeDatabaseAdaptor : DatabasePort {
    override fun createNewSummaryForChannels(channelNames: Collection<String>, comprehensionDate: LocalDate) {}
    override fun save(results: PresentableSentimentResults) {}
    override fun save(results: PresentableEntityResults) {}
    override fun save(results: PresentableKeyPhrasesResults) {}
}