package dev.number6.slackreader.model;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class WorkspaceMessages {
    private final LocalDate comprehensionDate;
    private final Map<String, Collection<String>> channelMessages;

    public WorkspaceMessages() {
        this(LocalDate.now());
    }

    public WorkspaceMessages(LocalDate comprehensionDate) {
        this.channelMessages = new HashMap<>();
        this.comprehensionDate = comprehensionDate;
    }

    public void add(String channelName, Collection<String> messages) {
        this.channelMessages.put(channelName, messages);
    }

    public LocalDate getComprehensionDate() {
        return comprehensionDate;
    }

    public Set<String> getChannelNames() {
        return channelMessages.keySet();
    }

    public Collection<String> getMessagesForChannel(String channelName) {
        return channelMessages.get(channelName);
    }

    public Set<String> getActiveChannelNames() {
        return getChannelNames().stream().filter(c -> getMessagesForChannel(c).size() > 0).collect(Collectors.toSet());
    }
}
