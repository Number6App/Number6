package dev.number6.slackposter;

import dev.number6.slackposter.dagger.DaggerTestSlackPosterComponent;
import dev.number6.slackposter.dagger.FakeSlackPosterConfigurationModule;
import dev.number6.slackposter.dagger.RecordingHttpAdaptor;
import dev.number6.slackposter.dagger.TestSlackPosterComponent;
import dev.number6.slackposter.model.ChannelSummaryImageBuilder;
import dev.number6.slackposter.model.Chat;
import dev.number6.slackposter.model.PresentableChannelSummary;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SlackPosterComponentIntegrationTest {

    TestSlackPosterComponent testee = DaggerTestSlackPosterComponent.create();
    Gson gson = new Gson();

    @Test
    void postCorrectChatToSlack() {
        ChannelSummaryImage image = ChannelSummaryImageBuilder.finalImage().build();
        SlackService service = testee.handler();
        service.handleNewImage(image, new FakeLambdaLogger());
        RecordingHttpAdaptor http = (RecordingHttpAdaptor) testee.getRecordingHttpAdaptor();

        assertThat(http.getPosts()).hasSize(1);
        var chat = gson.fromJson(http.getPosts().get(FakeSlackPosterConfigurationModule.SLACK_POST_MESSAGE_URL),
                Chat.class);
        assertThat(chat.text).isEqualTo(new PresentableChannelSummary(image).getInitialMessageLine());
        assertThat(chat.channel).isEqualTo(FakeSlackPosterConfigurationModule.POSTING_CHANNEL_ID);
    }

    static class FakeLambdaLogger implements LambdaLogger {

        @Override
        public void log(String message) {
            System.out.println(message);
        }

        @Override
        public void log(byte[] message) {
            throw new UnsupportedOperationException("doin' wut now?");
        }
    }
}
