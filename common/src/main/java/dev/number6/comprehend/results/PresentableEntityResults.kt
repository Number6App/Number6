package dev.number6.comprehend.results

import java.time.LocalDate

class PresentableEntityResults(comprehensionDate: LocalDate,
                               entitiesResults: Map<String, Map<String, Long>>,
                               channelName: String) :
        ComprehensionResults<Map<String, Map<String, Long>>>(comprehensionDate, entitiesResults, channelName) {
    override fun toString(): String {
        return "EntityResults{" +
                "comprehensionDate=" + comprehensionDate +
                ", entitiesResults=" + results +
                ", channelName='" + channelName + '\'' +
                '}'
    }
}