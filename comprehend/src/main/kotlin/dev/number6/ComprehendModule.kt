package dev.number6

import dagger.Module
import dev.number6.comprehend.dagger.ComprehensionServiceModule
import dev.number6.db.dagger.DynamoDBMapperModule
import dev.number6.db.dagger.FullDatabasePortModule

@Module(includes = [DynamoDBMapperModule::class,
    FullDatabasePortModule::class,
    ComprehensionServiceModule::class])
class ComprehendModule