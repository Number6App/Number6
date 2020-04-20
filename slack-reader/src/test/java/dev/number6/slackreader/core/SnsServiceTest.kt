package dev.number6.slackreader.core

import dev.number6.message.ChannelMessages
import dev.number6.slackreader.SnsService
import dev.number6.slackreader.generate.SlackReaderRDG
import dev.number6.slackreader.model.WorkspaceMessages
import dev.number6.slackreader.port.NotificationPort
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import uk.org.fyodor.generators.RDG
import uk.org.fyodor.range.Range
import java.time.LocalDate
import java.util.*

@ExtendWith(MockitoExtension::class)
@Disabled("work out what to do with Mockito")
internal class SnsServiceTest {
    @Mock
    private val notificationPort: NotificationPort? = null
    private var testee: SnsService? = null

//    @BeforeEach
//    fun setup() {
//        testee = SnsService(notificationPort)
//    }

//    @Test
//    fun sendMessagesChannelToSns() {
//        val workspaceMessages = WorkspaceMessages(LocalDate.now())
//        val channelName = RDG.string().next()
//        val messages = RDG.list(RDG.string(), Range.closed(10, 30)).next()
//        workspaceMessages.add(channelName, messages)
//        testee.broadcastWorkspaceMessagesForActiveChannels(workspaceMessages)
//        val captor = ArgumentCaptor.forClass(ChannelMessages::class.java)
//        Mockito.verify(notificationPort, Mockito.times(1)).broadcast(captor.capture())
//        Assertions.assertThat(captor.value.comprehensionDate).isEqualTo(LocalDate.now())
//        Assertions.assertThat(captor.value.channelName).isEqualTo(channelName)
//        Assertions.assertThat(captor.value.messages).hasSameElementsAs(messages)
//    }
//
//    @Test
//    fun sendMessagesForActiveChannelToSns() {
//        val workspaceMessages = SlackReaderRDG.workspaceMessages(Range.closed(5, 20)).next()
//        val channelName = RDG.string().next()
//        workspaceMessages.add(channelName, ArrayList())
//        testee.broadcastWorkspaceMessagesForActiveChannels(workspaceMessages)
//        val captor = ArgumentCaptor.forClass(ChannelMessages::class.java)
//        Mockito.verify(notificationPort, Mockito.times(workspaceMessages.activeChannelNames.size)).broadcast(captor.capture())
//        Assertions.assertThat(captor.allValues).hasSize(workspaceMessages.activeChannelNames.size)
//        for (channelMessages in captor.allValues) {
//            Assertions.assertThat(channelMessages.comprehensionDate).isEqualTo(LocalDate.now())
//            Assertions.assertThat(channelMessages.channelName).isIn(workspaceMessages.activeChannelNames)
//            Assertions.assertThat(channelMessages.messages).containsExactlyInAnyOrderElementsOf(workspaceMessages.getMessagesForChannel(channelMessages.channelName))
//        }
//    }
}