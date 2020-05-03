package dev.number6.db.dagger

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import dagger.Module
import dagger.Provides
import dev.number6.db.adaptor.DynamoFullDatabaseAdaptor
import dev.number6.db.port.DatabaseConfigurationPort
import dev.number6.db.port.FullDatabasePort

@Module(includes = [DatabaseConfigurationPortModule::class])
class FullDatabasePortModule {
    @Provides
    fun dbService(mapper: DynamoDBMapper, dbConfig: DatabaseConfigurationPort): FullDatabasePort {
        return DynamoFullDatabaseAdaptor(mapper, dbConfig)
    }
}