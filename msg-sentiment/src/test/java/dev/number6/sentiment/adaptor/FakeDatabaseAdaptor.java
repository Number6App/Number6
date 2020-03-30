package dev.number6.sentiment.adaptor;

import dev.number6.comprehend.results.PresentableEntityResults;
import dev.number6.comprehend.results.PresentableKeyPhrasesResults;
import dev.number6.comprehend.results.PresentableSentimentResults;
import dev.number6.db.port.DatabasePort;

import java.time.LocalDate;
import java.util.Collection;

public class FakeDatabaseAdaptor implements DatabasePort {
    @Override
    public void createNewSummaryForChannels(Collection<String> channelNames, LocalDate comprehensionDate) {

    }

    @Override
    public void save(PresentableSentimentResults results) {

    }

    @Override
    public void save(PresentableEntityResults results) {

    }

    @Override
    public void save(PresentableKeyPhrasesResults results) {

    }
}
