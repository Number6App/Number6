package dev.number6.db.dagger;

import dev.number6.db.port.DatabaseConfigurationPort;
import dev.number6.db.adaptor.DatabaseServiceConfigurationAdaptor;
import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseConfigurationPortModule {

    @Provides
    DatabaseConfigurationPort dbConfig() {
        return new DatabaseServiceConfigurationAdaptor();
    }
}
