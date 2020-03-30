package dev.number6.db.model.converter;

import dev.number6.db.model.SentimentTotal;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;

public class SentimentTotalConverter implements DynamoDBTypeConverter<String, Collection<SentimentTotal>> {

    private final Gson gson = new Gson();

    @Override
    public String convert(Collection<SentimentTotal> totals) {
        return gson.toJson(totals);
    }

    @Override
    public Collection<SentimentTotal> unconvert(String object) {
        Type type = new TypeToken<Collection<SentimentTotal>>() {
        }.getType();
        return gson.fromJson(object, type);
    }
}
