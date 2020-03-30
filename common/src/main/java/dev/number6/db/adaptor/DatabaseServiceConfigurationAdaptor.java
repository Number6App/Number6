package dev.number6.db.adaptor;

import dev.number6.db.port.DatabaseConfigurationPort;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;

public class DatabaseServiceConfigurationAdaptor implements DatabaseConfigurationPort {

    public static final String DB_TABLE = "DB_TABLE";
    private final DynamoDBMapperConfig dynamoDBMapperConfig;

    public DatabaseServiceConfigurationAdaptor() {
        dynamoDBMapperConfig = new DynamoDBMapperConfig.TableNameOverride(System.getenv(DB_TABLE)).config();
    }

    @Override
    public DynamoDBMapperConfig getDynamoDBMapperConfig() {
        return dynamoDBMapperConfig;
    }
}
