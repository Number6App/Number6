package dev.number6;

import dev.number6.comprehend.dagger.ComprehensionServiceModule;
import dev.number6.db.dagger.DatabaseConfigurationPortModule;
import dev.number6.db.dagger.DatabasePortModule;
import dev.number6.db.dagger.DynamoDBMapperModule;
import dagger.Module;

@Module(includes = {DynamoDBMapperModule.class,
        DatabasePortModule.class,
        DatabaseConfigurationPortModule.class,
        ComprehensionServiceModule.class})
public class CommonModule {
}
