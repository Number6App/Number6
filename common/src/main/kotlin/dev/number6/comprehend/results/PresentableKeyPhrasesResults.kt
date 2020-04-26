package dev.number6.comprehend.results

import java.time.LocalDate

class PresentableKeyPhrasesResults(comprehensionDate: LocalDate,
                                   keyPhrasesResults: Map<String, Long>,
                                   channelName: String) :
        ComprehensionResults<Map<String, Long>>(comprehensionDate, keyPhrasesResults, channelName) {
    override fun toString(): String {
        return "KeyPhrasesResults{" +
                "comprehensionDate=" + comprehensionDate +
                ", keyPhrasesResults=" + results +
                ", channelName='" + channelName + '\'' +
                '}'
    }
}