package dev.number6.slackreader.model;

import java.util.Collection;

public class ChannelsListResponse {
    private final Boolean ok;
    private final Collection<Channel> channels;

    public ChannelsListResponse(Boolean ok, Collection<Channel> channels) {
        this.ok = ok;
        this.channels = channels;
    }

    public Boolean getOk() {
        return ok;
    }

    public Collection<Channel> getChannels() {
        return channels;
    }

    @Override
    public String toString() {
        return "ChannelsListResponse{" +
                "ok=" + ok +
                ", channels=" + channels +
                '}';
    }
}
