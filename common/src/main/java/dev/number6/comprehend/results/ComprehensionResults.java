package dev.number6.comprehend.results;

import java.time.LocalDate;

public class ComprehensionResults<T> {

    private LocalDate comprehensionDate;
    private T results;
    private String channelName;

    public ComprehensionResults(LocalDate comprehensionDate, T results, String channelName) {
        this.comprehensionDate = comprehensionDate;
        this.results = results;
        this.channelName = channelName;
    }

    public LocalDate getComprehensionDate() {
        return comprehensionDate;
    }

    public T getResults() {
        return results;
    }

    public String getChannelName() {
        return channelName;
    }

}
