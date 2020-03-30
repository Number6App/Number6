package dev.number6.db.model.converter;

import dev.number6.db.model.EntityTotal;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;

public class EntityTotalConverter implements DynamoDBTypeConverter<String, Collection<EntityTotal>> {

    private final Gson gson = new Gson();

    @Override
    public String convert(Collection<EntityTotal> totals) {
        return gson.toJson(totals);
    }

    @Override
    public Collection<EntityTotal> unconvert(String object) {
        Type type = new TypeToken<Collection<EntityTotal>>() {
        }.getType();
        return gson.fromJson(object, type);
    }
}
