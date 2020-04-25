package dev.number6.db.model.converter

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.number6.db.model.SentimentTotal

class SentimentTotalConverter : DynamoDBTypeConverter<String, Collection<SentimentTotal>> {
    private val gson = Gson()
    override fun convert(totals: Collection<SentimentTotal>): String {
        return gson.toJson(totals)
    }

    override fun unconvert(`object`: String): Collection<SentimentTotal> {
        val type = object : TypeToken<Collection<SentimentTotal>>() {}.type
        return gson.fromJson(`object`, type)
    }
}