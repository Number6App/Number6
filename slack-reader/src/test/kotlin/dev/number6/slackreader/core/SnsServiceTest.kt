package dev.number6.slackreader.core

import assertk.assertThat
import assertk.assertions.containsOnly
import assertk.assertions.hasSameSizeAs
import assertk.assertions.isEqualTo
import assertk.assertions.isIn
import dev.number6.message.ChannelMessages
import dev.number6.slackreader.SnsService
import dev.number6.slackreader.generate.SlackReaderRDG
import dev.number6.slackreader.model.WorkspaceMessages
import dev.number6.slackreader.port.NotificationPort
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import uk.org.fyodor.generators.RDG
import uk.org.fyodor.range.Range
import java.time.LocalDate
import java.util.*

@ExtendWith(MockKExtension::class)
internal class SnsServiceTest {

    private val notificationPort: NotificationPort = mockk(relaxUnitFun = true)
    private val testee = SnsService(notificationPort)

    @Test
    fun sendMessagesChannelToSns() {
        val workspaceMessages = WorkspaceMessages(LocalDate.now())
        val expectedChannelName = RDG.string().next()
        val expectedMessages = RDG.list(RDG.string(), Range.closed(10, 30)).next()
        workspaceMessages.add(expectedChannelName, expectedMessages)
        testee.broadcastWorkspaceMessagesForActiveChannels(workspaceMessages)
        val slot = slot<ChannelMessages>()
        verify(exactly = 1) { notificationPort.broadcast(capture(slot)) }

        with(slot.captured) {
            assertThat(comprehensionDate).isEqualTo(LocalDate.now())
            assertThat(channelName).isEqualTo(expectedChannelName)
            assertThat(messages).containsOnly(*expectedMessages.toTypedArray())
        }
    }

    @Test
    fun sendMessagesForActiveChannelToSns() {
        val workspaceMessages = SlackReaderRDG.workspaceMessages(Range.closed(5, 20)).next()
        val channelName = RDG.string().next()
        workspaceMessages.add(channelName, ArrayList())
        testee.broadcastWorkspaceMessagesForActiveChannels(workspaceMessages)
        val broadcastMessages = mutableListOf<ChannelMessages>()
        verify(exactly = workspaceMessages.getActiveChannelNames().size) { notificationPort.broadcast(capture(broadcastMessages)) }
        assertThat(broadcastMessages).hasSameSizeAs(workspaceMessages.getActiveChannelNames())

        broadcastMessages.forEach {
            assertThat(it.comprehensionDate).isEqualTo(LocalDate.now())
            assertThat(it.channelName).isIn(*workspaceMessages.getActiveChannelNames().toTypedArray())
            assertThat(it.messages).containsOnly(*workspaceMessages.getMessagesForChannel(it.channelName).toTypedArray())
        }
    }
}