package dev.number6.db.port

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig

interface DatabaseConfigurationPort {
    val dynamoDBMapperConfig: DynamoDBMapperConfig
}