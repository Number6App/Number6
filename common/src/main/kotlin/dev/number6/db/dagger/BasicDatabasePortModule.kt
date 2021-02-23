package dev.number6.db.dagger

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import dagger.Module
import dagger.Provides
import dev.number6.db.adaptor.DynamoBasicDatabaseAdaptor
import dev.number6.db.port.DatabaseConfigurationPort
import dev.number6.db.port.BasicDatabasePort

@Module
class BasicDatabasePortModule {
    @Provides
    fun dbService(mapper: DynamoDBMapper, dbConfig: DatabaseConfigurationPort): BasicDatabasePort {
        return DynamoBasicDatabaseAdaptor(mapper, dbConfig)
    }
}