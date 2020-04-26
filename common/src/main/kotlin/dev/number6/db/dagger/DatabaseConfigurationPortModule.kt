package dev.number6.db.dagger

import dagger.Module
import dagger.Provides
import dev.number6.db.adaptor.DatabaseServiceConfigurationAdaptor
import dev.number6.db.port.DatabaseConfigurationPort

@Module
class DatabaseConfigurationPortModule {
    @Provides
    fun dbConfig(): DatabaseConfigurationPort {
        return DatabaseServiceConfigurationAdaptor()
    }
}