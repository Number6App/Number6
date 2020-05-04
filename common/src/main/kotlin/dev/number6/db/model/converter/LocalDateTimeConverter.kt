package dev.number6.db.model.converter

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter
import java.time.LocalDateTime

class LocalDateTimeConverter : DynamoDBTypeConverter<String, LocalDateTime> {
    override fun convert(dateTime: LocalDateTime): String {
        return dateTime.toString()
    }

    override fun unconvert(dateTime: String): LocalDateTime {
        return LocalDateTime.parse(dateTime)
    }
}