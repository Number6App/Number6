package dev.number6.slackreader.core

import assertk.assertThat
import assertk.assertions.doesNotContain
import assertk.assertions.hasSize
import dev.number6.slackreader.model.WorkspaceMessages
import org.junit.jupiter.api.Test
import uk.org.fyodor.generators.RDG
import uk.org.fyodor.range.Range
import java.util.*

internal class WorkspaceMessagesTest {

    @Test
    fun activeChannelNamesReturnsChannelsWithMessages() {
        val testee = WorkspaceMessages()
        val inactiveChannelName = "inactive"
        testee.add(RDG.string(Range.closed(5, 30)).next(), RDG.list(RDG.string(), Range.closed(10, 20)).next())
        testee.add(inactiveChannelName, ArrayList())
        testee.add(RDG.string(Range.closed(5, 30)).next(), RDG.list(RDG.string(), Range.closed(10, 20)).next())
        val activeChannelNames = testee.getActiveChannelNames()
        assertThat(activeChannelNames).hasSize(2)
        assertThat(activeChannelNames).doesNotContain(inactiveChannelName)
    }
}