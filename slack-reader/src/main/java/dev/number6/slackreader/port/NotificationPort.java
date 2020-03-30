package dev.number6.slackreader.port;

import dev.number6.message.ChannelMessages;

public interface NotificationPort {
    void broadcast(ChannelMessages messages);
}
