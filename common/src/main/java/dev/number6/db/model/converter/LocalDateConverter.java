package dev.number6.db.model.converter;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;

import java.time.LocalDate;

public class LocalDateConverter implements DynamoDBTypeConverter<String, LocalDate> {

    @Override
    public String convert(LocalDate object) {
        return object.toString();
    }

    @Override
    public LocalDate unconvert(String object) {
        return LocalDate.parse(object);
    }
}
