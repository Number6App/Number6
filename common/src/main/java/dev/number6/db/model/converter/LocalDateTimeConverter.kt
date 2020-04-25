package dev.number6.db.model.converter

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter
import java.time.LocalDateTime

class LocalDateTimeConverter : DynamoDBTypeConverter<String, LocalDateTime> {
    override fun convert(`object`: LocalDateTime): String {
        return `object`.toString()
    }

    override fun unconvert(`object`: String): LocalDateTime {
        return LocalDateTime.parse(`object`)
    }
}