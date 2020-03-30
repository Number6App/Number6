package dev.number6.db.dagger;

import dev.number6.db.adaptor.DynamoDatabaseAdaptor;
import dev.number6.db.port.DatabaseConfigurationPort;
import dev.number6.db.port.DatabasePort;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import dagger.Module;
import dagger.Provides;

@Module(includes = {
        DatabaseConfigurationPortModule.class
})
public class DatabasePortModule {

    @Provides
    public DatabasePort dbService(DynamoDBMapper mapper, DatabaseConfigurationPort dbConfig) {
        return new DynamoDatabaseAdaptor(mapper, dbConfig);
    }

}
