package dev.number6.comprehend.results;

import java.time.LocalDate;
import java.util.Map;

public class PresentableEntityResults extends ComprehensionResults<Map<String, Map<String, Long>>> {

    public PresentableEntityResults(LocalDate comprehensionDate, Map<String, Map<String, Long>> entitiesResults, String channelName) {
        super(comprehensionDate, entitiesResults, channelName);
    }

    @Override
    public String toString() {
        return "EntityResults{" +
                "comprehensionDate=" + getComprehensionDate() +
                ", entitiesResults=" + getResults() +
                ", channelName='" + getChannelName() + '\'' +
                '}';
    }
}
