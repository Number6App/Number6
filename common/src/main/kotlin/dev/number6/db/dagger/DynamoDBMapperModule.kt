package dev.number6.db.dagger

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import dagger.Module
import dagger.Provides

@Module
class DynamoDBMapperModule {
    @Provides
    fun dbMapper(): DynamoDBMapper {
        val client = AmazonDynamoDBClientBuilder.defaultClient()
        return DynamoDBMapper(client)
    }
}