package dev.number6.slack

import com.amazonaws.services.lambda.runtime.LambdaLogger
import dev.number6.slack.adaptor.SlackClientAdaptor
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class SlackClientIntegrationTest {
    var testee = DaggerTestSlackClientComponent.create()

    @Test
    fun get() {
        val client = testee.slackPort()
        val response = client.getSlackResponse(SlackClientAdaptor.CHANNEL_HISTORY_URL, String::class.java, FakeLogger())
        Assertions.assertThat(response.isPresent()).isTrue()
        Assertions.assertThat(response.get()).isEqualTo("response")
    }

    @Test
    fun post() {
        val client = testee.slackPort()
        val response = client.getSlackResponse(SlackClientAdaptor.CHANNEL_HISTORY_URL, FakeLogger(), "body")
        Assertions.assertThat(response.isPresent()).isFalse()
    }

    @Test
    fun postWithResponse() {
        val client = testee.slackPort()
        val response = client.getSlackResponse(SlackClientAdaptor.CHANNEL_HISTORY_URL, "body", String::class.java, FakeLogger())
        Assertions.assertThat(response.isPresent()).isTrue()
        Assertions.assertThat(response.get()).isEqualTo("response")
    }

    private class FakeLogger : LambdaLogger {
        override fun log(message: String) {}
        override fun log(message: ByteArray) {}
    }
}