package dev.number6.message;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Objects;

public class ChannelMessages {

    @JsonProperty
    private LocalDate comprehensionDate;
    @JsonProperty
    private String channelName;
    @JsonProperty
    private Collection<String> messages;

    public ChannelMessages() {
    }

    public ChannelMessages(String channelName, Collection<String> messages, LocalDate comprehensionDate) {
        this.channelName = channelName;
        this.messages = messages;
        this.comprehensionDate = comprehensionDate;
    }

    public LocalDate getComprehensionDate() {
        return comprehensionDate;
    }

    public String getChannelName() {
        return channelName;
    }

    public Collection<String> getMessages() {
        return messages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChannelMessages that = (ChannelMessages) o;
        return Objects.equals(comprehensionDate, that.comprehensionDate) &&
                Objects.equals(channelName, that.channelName) &&
                Objects.equals(messages, that.messages);
    }

    @Override
    public int hashCode() {

        return Objects.hash(comprehensionDate, channelName, messages);
    }
}