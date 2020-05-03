package dev.number6.db.adaptor

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import dev.number6.db.model.ChannelComprehensionSummary
import dev.number6.db.port.BasicDatabasePort
import dev.number6.db.port.DatabaseConfigurationPort
import java.time.LocalDate
import java.util.function.Consumer

open class DynamoBasicDatabaseAdaptor(private val mapper: DynamoDBMapper, private val dbConfig: DatabaseConfigurationPort) : BasicDatabasePort {
    override fun createNewSummaryForChannels(channelNames: Collection<String>, comprehensionDate: LocalDate) {
        channelNames.forEach(Consumer { c: String -> createNewSummaryForChannel(c, comprehensionDate) })
    }

    private fun createNewSummaryForChannel(channelName: String, comprehensionDate: LocalDate) {
        mapper.save(ChannelComprehensionSummary(channelName, comprehensionDate), dbConfig.dynamoDBMapperConfig)
    }

}