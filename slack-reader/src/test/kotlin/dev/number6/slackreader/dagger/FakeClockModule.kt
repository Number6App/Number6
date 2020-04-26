package dev.number6.slackreader.dagger

import dagger.Module
import dagger.Provides
import uk.org.fyodor.generators.RDG
import uk.org.fyodor.range.Range
import java.time.Clock
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import javax.inject.Singleton

@Module
class FakeClockModule {
    @Provides
    @Singleton
    fun providesClock(): Clock {
        return Clock.fixed(LocalDateTime.now().minusDays(RDG.longVal(Range.closed(2L, 10L)).next()).toInstant(ZoneOffset.UTC), ZoneId.systemDefault())
    }
}