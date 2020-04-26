package dev.number6.db.dagger

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import dagger.Module
import dagger.Provides
import dev.number6.db.adaptor.DynamoDatabaseAdaptor
import dev.number6.db.port.DatabaseConfigurationPort
import dev.number6.db.port.DatabasePort

@Module(includes = [DatabaseConfigurationPortModule::class])
class DatabasePortModule {
    @Provides
    fun dbService(mapper: DynamoDBMapper, dbConfig: DatabaseConfigurationPort): DatabasePort {
        return DynamoDatabaseAdaptor(mapper, dbConfig)
    }
}