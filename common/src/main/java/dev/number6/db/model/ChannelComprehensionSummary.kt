package dev.number6.db.model

import com.amazonaws.services.dynamodbv2.datamodeling.*
import dev.number6.db.model.converter.LocalDateConverter
import dev.number6.db.model.converter.LocalDateTimeConverter
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

//used by DynamoDB
@DynamoDBTable(tableName = "SlackComprehension")
class ChannelComprehensionSummary {
    @DynamoDBTypeConverted(converter = LocalDateTimeConverter::class)
    @DynamoDBAttribute
    var createDate = LocalDateTime.now()

    @DynamoDBTypeConverted(converter = LocalDateConverter::class)
    @DynamoDBRangeKey
    var comprehensionDate: LocalDate? = null

    @DynamoDBHashKey
    var channelName: String? = null

    //    @DynamoDBTypeConverted(converter = SentimentScoreConverter.class)
    @DynamoDBAttribute
    var sentimentScoreTotals: Map<String, Float> = HashMap()

    //    @DynamoDBTypeConverted(converter = SentimentTotalConverter.class)
    @DynamoDBAttribute
    var sentimentTotals: Map<String, Int> = HashMap()

    @DynamoDBAttribute
    var keyPhrasesTotals: Map<String, Long> = HashMap()

    //    @DynamoDBTypeConverted(converter = EntityTotalConverter.class)
    @DynamoDBAttribute
    var entityTotals: Map<String, Map<String, Long>> = HashMap()

    @DynamoDBVersionAttribute
    var version: Int? = null

    constructor() {}
    constructor(channelName: String?, comprehensionDate: LocalDate?) {
        this.channelName = channelName
        this.comprehensionDate = comprehensionDate
    }
}