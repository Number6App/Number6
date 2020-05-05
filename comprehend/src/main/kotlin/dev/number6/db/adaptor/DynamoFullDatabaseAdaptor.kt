package dev.number6.db.adaptor

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException
import dev.number6.comprehend.results.ComprehensionResults
import dev.number6.comprehend.results.PresentableEntityResults
import dev.number6.comprehend.results.PresentableKeyPhrasesResults
import dev.number6.comprehend.results.PresentableSentimentResults
import dev.number6.db.model.ChannelComprehensionSummary
import dev.number6.db.port.DatabaseConfigurationPort
import dev.number6.db.port.FullDatabasePort

typealias SummaryUpdate<T> = (s: ChannelComprehensionSummary, r: T) -> Unit

class DynamoFullDatabaseAdaptor(private val mapper: DynamoDBMapper, private val dbConfig: DatabaseConfigurationPort) :
        DynamoBasicDatabaseAdaptor(mapper, dbConfig), FullDatabasePort {

    override fun save(results: PresentableSentimentResults) {
        save(results, { s: ChannelComprehensionSummary, r: PresentableSentimentResults ->
            s.sentimentTotals = r.sentimentTotals
            s.sentimentScoreTotals = r.sentimentScoreTotals
        })
    }

    override fun save(results: PresentableEntityResults) {
        save(results, { s: ChannelComprehensionSummary, r: PresentableEntityResults -> s.entityTotals = r.presentableResults })
    }

    override fun save(results: PresentableKeyPhrasesResults) {
        save(results, { s: ChannelComprehensionSummary, r: PresentableKeyPhrasesResults -> s.keyPhrasesTotals = r.presentableResults })
    }

    private fun <T : ComprehensionResults<*, *>> save(results: T,
                                                      summaryUpdater: SummaryUpdate<T>) {
        val dbSummaries = mapper.load(ChannelComprehensionSummary::class.java,
                results.channelName,
                results.comprehensionDate,
                dbConfig.dynamoDBMapperConfig)
        summaryUpdater.invoke(dbSummaries, results)
        try {
            mapper.save(dbSummaries, dbConfig.dynamoDBMapperConfig)
        } catch (e: ConditionalCheckFailedException) {
            save(results, summaryUpdater)
        }
    }
}