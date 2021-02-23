package dev.number6.db.model.converter

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter
import java.time.LocalDate

class LocalDateConverter : DynamoDBTypeConverter<String, LocalDate> {
    override fun convert(date: LocalDate): String {
        return date.toString()
    }

    override fun unconvert(date: String): LocalDate {
        return LocalDate.parse(date)
    }
}