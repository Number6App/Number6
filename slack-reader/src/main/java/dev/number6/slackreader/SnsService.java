package dev.number6.slackreader;

import dev.number6.slackreader.model.WorkspaceMessages;
import dev.number6.slackreader.port.NotificationPort;
import dev.number6.message.ChannelMessages;

public class SnsService {

    private final NotificationPort notifications;

    public SnsService(NotificationPort notifications) {
        this.notifications = notifications;
    }

    public void broadcastWorkspaceMessagesForActiveChannels(WorkspaceMessages messages) {
        messages.getActiveChannelNames()
                .stream()
                .map(m -> new ChannelMessages(m, messages.getMessagesForChannel(m), messages.getComprehensionDate()))
                .forEach(notifications::broadcast);
    }
}
