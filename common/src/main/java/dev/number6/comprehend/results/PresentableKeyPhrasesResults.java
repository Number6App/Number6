package dev.number6.comprehend.results;

import java.time.LocalDate;
import java.util.Map;

public class PresentableKeyPhrasesResults extends ComprehensionResults<Map<String, Long>> {

    public PresentableKeyPhrasesResults(LocalDate comprehensionDate, Map<String, Long> keyPhrasesResults, String channelName) {
        super(comprehensionDate, keyPhrasesResults, channelName);
    }

    @Override
    public String toString() {
        return "KeyPhrasesResults{" +
                "comprehensionDate=" + getComprehensionDate() +
                ", keyPhrasesResults=" + getResults() +
                ", channelName='" + getChannelName() + '\'' +
                '}';
    }
}
