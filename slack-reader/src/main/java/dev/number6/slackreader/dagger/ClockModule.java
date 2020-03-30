package dev.number6.slackreader.dagger;

import dagger.Module;
import dagger.Provides;

import java.time.Clock;

@Module
public class ClockModule {

    @Provides
    Clock providesClock() {
        return Clock.systemUTC();
    }
}
