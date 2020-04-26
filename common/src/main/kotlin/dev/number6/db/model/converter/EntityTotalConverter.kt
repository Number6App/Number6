package dev.number6.db.model.converter

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.number6.db.model.EntityTotal

class EntityTotalConverter : DynamoDBTypeConverter<String, Collection<EntityTotal>> {
    private val gson = Gson()
    override fun convert(totals: Collection<EntityTotal>): String {
        return gson.toJson(totals)
    }

    override fun unconvert(`object`: String): Collection<EntityTotal> {
        val type = object : TypeToken<Collection<EntityTotal>>() {}.type
        return gson.fromJson(`object`, type)
    }
}