package dev.number6.slackreader.dagger

import com.amazonaws.services.dynamodbv2.AbstractAmazonDynamoDB
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression
import dagger.Module
import dagger.Provides
import dev.number6.db.port.DatabaseConfigurationPort
import java.util.*
import javax.inject.Singleton

@Module
class FakeDynamoDbMapperModule {
    @Provides
    fun providesDbMapper(dbConfig: DatabaseConfigurationPort, client: FakeAmazonDynamoDB): DynamoDBMapper {
        return object : DynamoDBMapper(client) {
            override fun <T> save(obj: T, saveExpression: DynamoDBSaveExpression?, config: DynamoDBMapperConfig?) {
                client.save(obj!!)
            }
        }
    }

    @Provides
    @Singleton
    fun providesAmazonDynamoDB(): FakeAmazonDynamoDB {
        return FakeAmazonDynamoDB()
    }

    inner class FakeAmazonDynamoDB : AbstractAmazonDynamoDB() {
        private val savedObjects: MutableCollection<Any> = mutableListOf()
        fun getSavedObjects(): MutableCollection<Any> {
            return savedObjects
        }

        fun save(o: Any) {
            savedObjects.add(o)
        }
    }
}