package dev.number6.slackreader.core

import com.amazonaws.services.lambda.runtime.LambdaLogger
import dev.number6.slackreader.SlackService
import dev.number6.slackreader.generate.SlackReaderRDG
import dev.number6.slackreader.model.Channel
import dev.number6.slackreader.model.Message
import dev.number6.slackreader.port.SlackPort
import dev.number6.slackreader.port.SlackReaderConfigurationPort
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.invocation.InvocationOnMock
import org.mockito.junit.jupiter.MockitoExtension
import uk.org.fyodor.generators.RDG
import uk.org.fyodor.range.Range
import java.time.LocalDate
import java.util.*
import java.util.function.Consumer
import java.util.stream.Collectors

@ExtendWith(MockitoExtension::class)
@Disabled("work out what to do with Mockito")
internal class SlackServiceTest {
    @Mock
    private val logger: LambdaLogger? = null

    @Mock
    private val slackPort: SlackPort? = null

    @Mock
    private val config: SlackReaderConfigurationPort? = null
    private val channelsGenerator = SlackReaderRDG.channels(Range.closed(5, 20))
    private val messagesGenerator = RDG.list(SlackReaderRDG.message(), Range.closed(5, 50))
    private var testee: SlackService? = null

//    @BeforeEach
//    fun setup() {
//        testee = SlackService(slackPort, config)
//    }

//    @Test
//    fun getMessagesOnDate() {
//        val channels = channelsGenerator.next()
//        val expectedMessages = channels.stream().collect(Collectors.toMap({ obj: Channel? -> obj.getName() }) { c: Channel? -> messagesGenerator.next() })
//        Mockito.`when`(slackPort.getChannelList(logger)).thenReturn(channels)
//        Mockito.`when`(slackPort.getMessagesForChannelOnDate(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any())).then { i: InvocationOnMock? -> expectedMessages[(i.getArgument<Any?>(0) as Channel).name] }
//        val messages = testee.getMessagesOnDate(LocalDate.now(), logger)
//        val channelNames = channels.stream().map { obj: Channel? -> obj.getName() }.collect(Collectors.toList())
//        Assertions.assertThat(messages.channelNames).containsExactlyInAnyOrderElementsOf(channelNames)
//        messages.channelNames.forEach(Consumer { name: String? ->
//            Assertions.assertThat(messages.getMessagesForChannel(name))
//                    .containsExactlyInAnyOrderElementsOf(expectedMessages[name].stream().map { obj: Message? -> obj.getText() }.collect(Collectors.toList()))
//        })
//    }
//
//    @Test
//    fun ignoreMessagesForBlacklistedChannels() {
//        val channel1Name = "channel 1 name"
//        val channel2Name = "channel 2 name"
//        Mockito.`when`(config.getBlacklistedChannels()).thenReturn(Arrays.asList(channel1Name, channel2Name))
//        val channels = channelsGenerator.next()
//        channels.add(Channel("whatever", channel1Name))
//        channels.add(Channel("whatever2", channel2Name))
//        Mockito.`when`(slackPort.getChannelList(logger)).thenReturn(channels)
//        val messages = testee.getMessagesOnDate(LocalDate.now(), logger)
//        Assertions.assertThat(messages.channelNames).doesNotContain(channel1Name, channel2Name)
//    }
}