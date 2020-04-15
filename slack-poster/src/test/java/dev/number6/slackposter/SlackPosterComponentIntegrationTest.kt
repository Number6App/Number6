package dev.number6.slackposter

import com.amazonaws.services.lambda.runtime.LambdaLogger
import com.google.gson.Gson
import dev.number6.slackposter.dagger.DaggerTestSlackPosterComponent
import dev.number6.slackposter.dagger.FakeSlackPosterConfigurationModule
import dev.number6.slackposter.dagger.RecordingHttpAdaptor
import dev.number6.slackposter.model.ChannelSummaryImageBuilder
import dev.number6.slackposter.model.Chat
import dev.number6.slackposter.model.PresentableChannelSummary
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class SlackPosterComponentIntegrationTest {
    var testee = DaggerTestSlackPosterComponent.create()
    var gson = Gson()

    @Test
    fun postCorrectChatToSlack() {
        val image: ChannelSummaryImage = ChannelSummaryImageBuilder.Companion.finalImage().build()
        val service = testee.handler()
        service.handleNewImage(image, FakeLambdaLogger())
        val http = testee.recordingHttpAdaptor as RecordingHttpAdaptor
        Assertions.assertThat(http.getPosts()).hasSize(1)
        val chat = gson.fromJson(http.getPosts()[FakeSlackPosterConfigurationModule.Companion.SLACK_POST_MESSAGE_URL],
                Chat::class.java)
        Assertions.assertThat(chat.text).isEqualTo(PresentableChannelSummary(image).initialMessageLine)
        Assertions.assertThat(chat.channel).isEqualTo(FakeSlackPosterConfigurationModule.Companion.POSTING_CHANNEL_ID)
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