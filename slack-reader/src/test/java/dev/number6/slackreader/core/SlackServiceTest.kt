package dev.number6.slackreader.core

import assertk.assertThat
import assertk.assertions.containsNone
import assertk.assertions.containsOnly
import com.amazonaws.services.lambda.runtime.LambdaLogger
import dev.number6.slackreader.SlackService
import dev.number6.slackreader.generate.SlackReaderRDG
import dev.number6.slackreader.model.Channel
import dev.number6.slackreader.model.Message
import dev.number6.slackreader.port.SlackPort
import dev.number6.slackreader.port.SlackReaderConfigurationPort
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import uk.org.fyodor.generators.Generator
import uk.org.fyodor.generators.RDG
import uk.org.fyodor.range.Range
import java.time.LocalDate

@ExtendWith(MockKExtension::class)
internal class SlackServiceTest {
    private val logger: LambdaLogger = mockk(relaxUnitFun = true)

    private val slackPort: SlackPort = mockk(relaxUnitFun = true)

    private val config: SlackReaderConfigurationPort = mockk(relaxUnitFun = true)

    private val channelsGenerator = SlackReaderRDG.channels(Range.closed(5, 20))
    private val messagesGenerator: Generator<List<Message>> = RDG.list(SlackReaderRDG.message(), Range.closed(5, 50))
    private val testee: SlackService = SlackService(slackPort, config)

    @Test
    fun getMessagesOnDate() {
        every { config.getBlacklistedChannels() } returns listOf()
        val channels = channelsGenerator.next()
        val expectedMessages = channels.associateBy({ c -> c.name }, { messagesGenerator.next() })
        every { slackPort.getChannelList(logger) } returns channels
        every { slackPort.getMessagesForChannelOnDate(any(), any(), any()) } answers {
            expectedMessages[firstArg<Channel>().name] ?: listOf()
        }
        val messages = testee.getMessagesOnDate(LocalDate.now(), logger)
        val channelNames = channels.map { c -> c.name }
        assertThat(messages.getActiveChannelNames()).containsOnly(*channelNames.toTypedArray())

        messages.getActiveChannelNames().forEach { name ->
            assertThat(messages.getMessagesForChannel(name))
                    .containsOnly(*expectedMessages[name]?.map { m -> m.text }?.toTypedArray() ?: Array(0) { "" })
        }
    }

    @Test
    fun ignoreMessagesForBlacklistedChannels() {
        val channel1Name = "channel 1 name"
        val channel2Name = "channel 2 name"
        every { config.getBlacklistedChannels() } returns listOf(channel1Name, channel2Name)
        val channels = channelsGenerator.next()
        channels.add(Channel("whatever", channel1Name))
        channels.add(Channel("whatever2", channel2Name))
        every { slackPort.getChannelList(logger) } returns channels
        every { slackPort.getMessagesForChannelOnDate(any(), any(), any()) } returns messagesGenerator.next()
        val messages = testee.getMessagesOnDate(LocalDate.now(), logger)
        assertThat(messages.getActiveChannelNames()).containsNone(channel1Name, channel2Name)
    }
}