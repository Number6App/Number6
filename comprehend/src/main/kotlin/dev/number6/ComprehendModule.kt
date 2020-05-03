package dev.number6

import dagger.Module
import dev.number6.comprehend.dagger.ComprehensionServiceModule
import dev.number6.db.dagger.FullDatabasePortModule
import dev.number6.db.dagger.DynamoDBMapperModule

@Module(includes = [DynamoDBMapperModule::class,
    FullDatabasePortModule::class,
//    DatabaseConfigurationPortModule::class,
    ComprehensionServiceModule::class])
class ComprehendModule