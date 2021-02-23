package dev.number6.db.adaptor

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig.TableNameOverride
import dev.number6.db.port.DatabaseConfigurationPort

class DatabaseServiceConfigurationAdaptor : DatabaseConfigurationPort {
    override val dynamoDBMapperConfig: DynamoDBMapperConfig by lazy {
        TableNameOverride(System.getenv(DB_TABLE)).config()
    }

    companion object {
        const val DB_TABLE = "DB_TABLE"
    }
}