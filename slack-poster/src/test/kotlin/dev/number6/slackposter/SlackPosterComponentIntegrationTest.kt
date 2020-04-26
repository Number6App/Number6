package dev.number6.slackposter

import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.isEqualTo
import com.amazonaws.services.lambda.runtime.LambdaLogger
import com.google.gson.Gson
import dev.number6.slackposter.dagger.DaggerTestSlackPosterComponent
import dev.number6.slackposter.dagger.FakeSlackPosterConfigurationModule
import dev.number6.slackposter.dagger.RecordingHttpAdaptor
import dev.number6.slackposter.model.ChannelSummaryImageBuilder
import dev.number6.slackposter.model.Chat
import dev.number6.slackposter.model.PresentableChannelSummary
import org.junit.jupiter.api.Test

class SlackPosterComponentIntegrationTest {
    private var testee = DaggerTestSlackPosterComponent.create()
    var gson = Gson()

    @Test
    fun postCorrectChatToSlack() {
        val image: ChannelSummaryImage = ChannelSummaryImageBuilder.finalImage().build()
        val service = testee.handler()
        service.handleNewImage(image, FakeLambdaLogger())
        val http = testee.recordingHttpAdaptor as RecordingHttpAdaptor
        assertThat(http.getPosts()).hasSize(1)
        val chat = gson.fromJson(http.getPosts()[FakeSlackPosterConfigurationModule.SLACK_POST_MESSAGE_URL],
                Chat::class.java)
        assertThat(chat.text).isEqualTo(PresentableChannelSummary(image).initialMessageLine)
        assertThat(chat.channel).isEqualTo(FakeSlackPosterConfigurationModule.POSTING_CHANNEL_ID)
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