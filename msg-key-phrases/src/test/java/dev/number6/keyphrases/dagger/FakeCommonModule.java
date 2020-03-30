package dev.number6.keyphrases.dagger;

import dev.number6.comprehend.port.ComprehensionPort;
import dev.number6.db.port.DatabasePort;
import dev.number6.keyphrases.adaptor.FakeComprehensionAdaptor;
import dev.number6.keyphrases.adaptor.FakeDatabaseAdaptor;
import dagger.Module;
import dagger.Provides;

@Module
public class FakeCommonModule {

    @Provides
    public DatabasePort providesDatabasePort() {
        return new FakeDatabaseAdaptor();
    }

    @Provides
    public ComprehensionPort providesComprehensionPort() {
        return new FakeComprehensionAdaptor();
    }
}
