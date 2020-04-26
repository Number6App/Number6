package dev.number6.db.model.converter

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter
import java.time.LocalDate

class LocalDateConverter : DynamoDBTypeConverter<String, LocalDate> {
    override fun convert(`object`: LocalDate): String {
        return `object`.toString()
    }

    override fun unconvert(`object`: String): LocalDate {
        return LocalDate.parse(`object`)
    }
}