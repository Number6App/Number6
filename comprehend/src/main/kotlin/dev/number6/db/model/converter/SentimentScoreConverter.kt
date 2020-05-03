package dev.number6.db.model.converter

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.number6.db.model.SentimentScore

class SentimentScoreConverter : DynamoDBTypeConverter<String, Collection<SentimentScore>> {
    private val gson = Gson()
    override fun convert(scores: Collection<SentimentScore>): String {
        return gson.toJson(scores)
    }

    override fun unconvert(`object`: String): Collection<SentimentScore> {
        val type = object : TypeToken<Collection<SentimentScore>>() {}.type
        return gson.fromJson(`object`, type)
    }
}