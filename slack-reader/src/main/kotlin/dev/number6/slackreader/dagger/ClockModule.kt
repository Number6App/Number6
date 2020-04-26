package dev.number6.slackreader.dagger

import dagger.Module
import dagger.Provides

import java.time.Clock

@Module
class ClockModule {
    @Provides
    fun providesClock(): Clock {
        return Clock.systemUTC()
    }
}