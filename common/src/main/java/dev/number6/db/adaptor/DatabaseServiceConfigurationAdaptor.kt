package dev.number6.db.adaptor

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig.TableNameOverride
import dev.number6.db.port.DatabaseConfigurationPort

class DatabaseServiceConfigurationAdaptor : DatabaseConfigurationPort {
    override val dynamoDBMapperConfig: DynamoDBMapperConfig

    companion object {
        const val DB_TABLE = "DB_TABLE"
    }

    init {
        dynamoDBMapperConfig = TableNameOverride(System.getenv(DB_TABLE)).config()
    }
}