package dev.number6

import dagger.Module
import dev.number6.comprehend.dagger.ComprehensionServiceModule
import dev.number6.db.dagger.DatabaseConfigurationPortModule
import dev.number6.db.dagger.DatabasePortModule
import dev.number6.db.dagger.DynamoDBMapperModule

@Module(includes = [DynamoDBMapperModule::class,
    DatabasePortModule::class,
//    DatabaseConfigurationPortModule::class,
    ComprehensionServiceModule::class])
class CommonModule 