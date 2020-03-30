package dev.number6.db.port;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;

public interface DatabaseConfigurationPort {
    DynamoDBMapperConfig getDynamoDBMapperConfig();
}
