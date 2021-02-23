package dev.number6.keyphrases.dagger

import dagger.Module
import dagger.Provides
import dev.number6.comprehend.port.ComprehensionPort
import dev.number6.db.port.FullDatabasePort
import dev.number6.keyphrases.adaptor.FakeComprehensionAdaptor
import dev.number6.keyphrases.adaptor.FakeDatabaseAdaptor

@Module
class FakeComprehendModule {
    @Provides
    fun providesDatabasePort(): FullDatabasePort {
        return FakeDatabaseAdaptor()
    }

    @Provides
    fun providesComprehensionPort(): ComprehensionPort {
        return FakeComprehensionAdaptor()
    }
}