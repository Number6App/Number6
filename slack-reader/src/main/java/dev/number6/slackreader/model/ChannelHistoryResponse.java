package dev.number6.slackreader.model;

import java.util.Collection;

public class ChannelHistoryResponse {
    private final Boolean ok;
    private final Collection<Message> messages;
    private final Boolean has_more;
    private final Long oldest;
    private final Long latest;

    public ChannelHistoryResponse(Boolean ok, Collection<Message> messages, Boolean hasMore, Long oldest, Long latest) {
        this.ok = ok;
        this.messages = messages;
        this.has_more = hasMore;
        this.oldest = oldest;
        this.latest = latest;
    }

    public Boolean getOk() {
        return ok;
    }

    public Collection<Message> getMessages() {
        return messages;
    }

    public Boolean getHasMore() {
        return has_more;
    }

    public Long getOldest() {
        return oldest;
    }

    public Long getLatest() {
        return latest;
    }

    @Override
    public String toString() {
        return "ChannelHistoryResponse{" +
                "ok=" + ok +
                ", messages=" + messages +
                '}';
    }
}
