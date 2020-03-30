package dev.number6.slack;

import dev.number6.slack.adaptor.SlackClientAdaptor;
import dev.number6.slack.port.SlackPort;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class SlackClientIntegrationTest {

    TestSlackClientComponent testee = DaggerTestSlackClientComponent.create();

    @Test
    void get() {
        SlackPort client = testee.slackPort();

        Optional<String> response = client.getSlackResponse(SlackClientAdaptor.CHANNEL_HISTORY_URL, String.class, new FakeLogger());
        assertThat(response.isPresent()).isTrue();
        assertThat(response.get()).isEqualTo("response");
    }

    @Test
    void post() {
        SlackPort client = testee.slackPort();

        Optional<String> response = client.getSlackResponse(SlackClientAdaptor.CHANNEL_HISTORY_URL, new FakeLogger(), "body");
        assertThat(response.isPresent()).isFalse();
    }

    @Test
    void postWithResponse() {
        SlackPort client = testee.slackPort();

        Optional<String> response = client.getSlackResponse(SlackClientAdaptor.CHANNEL_HISTORY_URL, "body", String.class, new FakeLogger());
        assertThat(response.isPresent()).isTrue();
        assertThat(response.get()).isEqualTo("response");
    }

    private static class FakeLogger implements LambdaLogger {

        @Override
        public void log(String message) {
        }

        @Override
        public void log(byte[] message) {
        }
    }
}
