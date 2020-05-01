package dev.number6.slack

import assertk.assertThat
import assertk.assertions.isNotEmpty
import assertk.assertions.isTrue
import com.amazonaws.services.lambda.runtime.LambdaLogger
import dev.number6.slack.dagger.DaggerTestSlackClientComponent
import dev.number6.slack.generate.SlackRDG
import io.mockk.mockk
import org.junit.jupiter.api.Test
import java.time.LocalDate

class SlackClientIntegrationTest {
    private val logger: LambdaLogger = mockk(relaxUnitFun = true)
    private var testee = DaggerTestSlackClientComponent.create()

    @Test
    fun joinChannel() {
        val c = SlackRDG.channel().next()
        val joinChannelResponse = testee.slackPort().joinChannel(c, logger)
        assertThat(joinChannelResponse.ok).isTrue()
    }

    @Test
    fun getChannelListAndMessages() {
        val channelsListResponse = testee.slackPort().getChannelList(logger)
        println(channelsListResponse.channels)
        assertThat(channelsListResponse.ok).isTrue()
        assertThat(channelsListResponse.channels).isNotEmpty()
        channelsListResponse.channels.forEach { c ->
            val channelHistoryResponse = testee.slackPort().getMessagesForChannelOnDate(c, LocalDate.now(), logger)
            assertThat(channelHistoryResponse.ok).isTrue()
            assertThat(channelHistoryResponse.messages).isNotEmpty()
        }
    }

    @Test
    fun postMessage() {
        testee.slackPort().postMessageToChannel("Message Content", logger)
    }

    private class FakeLogger : LambdaLogger {
        override fun log(message: String) {}
        override fun log(message: ByteArray) {}
    }
}