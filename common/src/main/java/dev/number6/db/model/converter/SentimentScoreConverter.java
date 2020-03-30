package dev.number6.db.model.converter;

import dev.number6.db.model.SentimentScore;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;

public class SentimentScoreConverter implements DynamoDBTypeConverter<String, Collection<SentimentScore>> {

    private final Gson gson = new Gson();

    @Override
    public String convert(Collection<SentimentScore> scores) {
        return gson.toJson(scores);
    }

    @Override
    public Collection<SentimentScore> unconvert(String object) {
        Type type = new TypeToken<Collection<SentimentScore>>() {
        }.getType();
        return gson.fromJson(object, type);
    }
}
