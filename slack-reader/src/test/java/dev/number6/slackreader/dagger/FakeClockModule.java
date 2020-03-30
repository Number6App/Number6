package dev.number6.slackreader.dagger;

import dev.number6.slackreader.generate.SlackReaderRDG;
import dagger.Module;
import dagger.Provides;
import uk.org.fyodor.range.Range;

import javax.inject.Singleton;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

@Module
public class FakeClockModule {

    @Provides
    @Singleton
    Clock providesClock() {
        return Clock.fixed(LocalDateTime.now().minusDays(SlackReaderRDG.longVal(Range.closed(2L, 10L)).next()).toInstant(ZoneOffset.UTC), ZoneId.systemDefault());
    }
}
