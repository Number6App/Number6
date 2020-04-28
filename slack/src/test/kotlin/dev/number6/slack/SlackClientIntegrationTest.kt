package dev.number6.slack

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import com.amazonaws.services.lambda.runtime.LambdaLogger
import dev.number6.slack.adaptor.SlackClientAdaptor
import dev.number6.slack.dagger.DaggerTestSlackClientComponent
import dev.number6.slack.dagger.FakeHttpClientModule
import dev.number6.slack.dagger.FakeHttpClientModule_ProvidesFakeCallFactoryFactory
import org.junit.jupiter.api.Test

class SlackClientIntegrationTest {
    private var testee = DaggerTestSlackClientComponent.create()

    @Test
    fun get() {
        val client = testee.slackPort()
        val response = client.getSlackResponse(SlackClientAdaptor.CHANNEL_HISTORY_URL, String::class.java, FakeLogger())
        assertThat(response.isPresent).isTrue()
        assertThat(response.get()).isEqualTo(FakeHttpClientModule.FakeCallFactory.FAKE_CALL_RESPONSE_BODY)
    }

    @Test
    fun post() {
        val client = testee.slackPort()
        val response = client.postToSlackNoResponse(SlackClientAdaptor.CHANNEL_HISTORY_URL, FakeLogger(), "body")
        assertThat(response.isPresent).isFalse()
    }

    @Test
    fun postWithResponse() {
        val client = testee.slackPort()
        val response = client.callSlack(SlackClientAdaptor.CHANNEL_HISTORY_URL, "body", String::class.java, FakeLogger())
        assertThat(response.isPresent).isTrue()
        assertThat(response.get()).isEqualTo(FakeHttpClientModule.FakeCallFactory.FAKE_CALL_RESPONSE_BODY)
    }

    private class FakeLogger : LambdaLogger {
        override fun log(message: String) {}
        override fun log(message: ByteArray) {}
    }
}