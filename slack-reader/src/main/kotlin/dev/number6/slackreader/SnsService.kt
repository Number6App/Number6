package dev.number6.slackreader

import dev.number6.message.ChannelMessages
import dev.number6.slackreader.model.WorkspaceMessages
import dev.number6.slackreader.port.NotificationPort

class SnsService(private val notifications: NotificationPort) {
    fun broadcastWorkspaceMessagesForActiveChannels(messages: WorkspaceMessages) {
        messages.getActiveChannelNames()
                .map { m -> ChannelMessages(m, messages.getMessagesForChannel(m), messages.comprehensionDate) }
                .forEach { cm -> notifications.broadcast(cm) }
    }
}