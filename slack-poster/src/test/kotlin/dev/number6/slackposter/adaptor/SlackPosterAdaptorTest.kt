package dev.number6.slackposter.adaptor

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.amazonaws.services.lambda.runtime.LambdaLogger
import com.google.gson.Gson
import dev.number6.slack.port.SlackPort
import dev.number6.slackposter.model.ChannelSummaryImageBuilder
import dev.number6.slackposter.model.Chat
import dev.number6.slackposter.model.PresentableChannelSummary
import dev.number6.slackposter.port.SlackPosterConfigurationPort
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Test

internal class SlackPosterAdaptorTest {

    private val gson = Gson()
    private val logger: LambdaLogger = mockk(relaxUnitFun = true)
    private val slackPort: SlackPort = mockk(relaxUnitFun = true)
    private val slackPosterConfiguration: SlackPosterConfigurationPort = mockk()
    private val testee = SlackPosterAdaptor(slackPort, slackPosterConfiguration)

    @Test
    fun postsChatToSlackChannel() {
        every { slackPosterConfiguration.postingChannelId } returns "postingChannel"
        val summary = PresentableChannelSummary(ChannelSummaryImageBuilder.notFinalImage().build())

        testee.postMessageToChannel(summary, logger)
        val contentSlot = slot<String>()
        verify {
            slackPort.postMessageToChannel(capture(contentSlot), logger)
        }
        val chat = gson.fromJson(contentSlot.captured, Chat::class.java)
        assertThat(chat.text).isEqualTo(summary.initialMessageLine)
        chat.attachments.all { a ->
            summary.attachments.any { a2 ->
                a2.pretext == a.pretext &&
                        a2.fields.contentEquals(a.fields)
            }
        }

    }
}