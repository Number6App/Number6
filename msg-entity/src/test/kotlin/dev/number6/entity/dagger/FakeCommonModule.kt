package dev.number6.entity.dagger

import dagger.Module
import dagger.Provides
import dev.number6.comprehend.port.ComprehensionPort
import dev.number6.db.port.DatabasePort
import dev.number6.entity.adaptor.FakeComprehensionAdaptor
import dev.number6.entity.adaptor.FakeDatabaseAdaptor

@Module
class FakeCommonModule {
    @Provides
    fun providesDatabasePort(): DatabasePort {
        return FakeDatabaseAdaptor()
    }

    @Provides
    fun providesComprehensionPort(): ComprehensionPort {
        return FakeComprehensionAdaptor()
    }
}