package dev.number6.db.port;

import dev.number6.comprehend.results.PresentableEntityResults;
import dev.number6.comprehend.results.PresentableKeyPhrasesResults;
import dev.number6.comprehend.results.PresentableSentimentResults;

import java.time.LocalDate;
import java.util.Collection;

public interface DatabasePort {

    void createNewSummaryForChannels(Collection<String> channelNames, LocalDate comprehensionDate);

    void save(PresentableSentimentResults results);

    void save(PresentableEntityResults results);

    void save(PresentableKeyPhrasesResults results);
}
