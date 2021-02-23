package dev.number6.slackposter

import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.isEqualTo
import com.amazonaws.services.lambda.runtime.LambdaLogger
import com.google.gson.Gson
import dev.number6.slackposter.dagger.DaggerTestSlackPosterComponent
import dev.number6.slackposter.dagger.RecordingSlackPosterAdaptor
import dev.number6.slackposter.model.ChannelSummaryImageBuilder
import dev.number6.slackposter.model.PresentableChannelSummary
import org.junit.jupiter.api.Test

class SlackPosterComponentIntegrationTest {
    private var testee = DaggerTestSlackPosterComponent.create()

    @Test
    fun postCorrectChatToSlack() {
        val image: ChannelSummaryImage = ChannelSummaryImageBuilder.finalImage().build()
        val service = testee.handler()
        service.handleNewImage(image, FakeLambdaLogger())
        val http = testee.recordingHttpAdaptor() as RecordingSlackPosterAdaptor
        assertThat(http.posts).hasSize(1)
        val chat = http.posts[0]
        assertThat(chat.initialMessageLine).isEqualTo(PresentableChannelSummary(image).initialMessageLine)
        chat.attachments.all { a ->
            PresentableChannelSummary(image).attachments.any { a2 ->
                a2.pretext == a.pretext &&
                        a2.fields.contentEquals(a.fields)
            }
        }
    }

    internal class FakeLambdaLogger : LambdaLogger {
        override fun log(message: String) {
            println(message)
        }

        override fun log(message: ByteArray) {
            throw UnsupportedOperationException("doin' wut now?")
        }
    }
}