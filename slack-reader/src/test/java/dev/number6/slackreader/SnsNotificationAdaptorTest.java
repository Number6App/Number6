package dev.number6.slackreader;

import dev.number6.slackreader.adaptor.EnvironmentVariableSlackReaderConfigurationAdapter;
import dev.number6.slackreader.adaptor.SnsNotificationAdaptor;
import dev.number6.slackreader.port.SlackReaderConfigurationPort;
import dev.number6.slackreader.port.NotificationPort;
import dev.number6.slackreader.generate.SlackReaderRDG;
import dev.number6.message.ChannelMessages;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishRequest;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class SnsNotificationAdaptorTest {

    private static final String TOPIC_ARN = "Topic-Arn";

    private final Gson gson = new Gson();
    private final SlackReaderConfigurationPort config = mock(EnvironmentVariableSlackReaderConfigurationAdapter.class);
    private final AmazonSNS sns = mock(AmazonSNS.class);
    private NotificationPort testee;

    @BeforeEach
    void setup() {
        when(config.getTopicArn()).thenReturn(TOPIC_ARN);
        testee = new SnsNotificationAdaptor(config, sns);
    }

    @Test
    void sendSerializedChannelMessagesToSns() {
        ChannelMessages messages = new ChannelMessages(SlackReaderRDG.string().next(), SlackReaderRDG.list(SlackReaderRDG.string()).next(), LocalDate.now());
        testee.broadcast(messages);

        ArgumentCaptor<PublishRequest> captor = ArgumentCaptor.forClass(PublishRequest.class);
        verify(sns).publish(captor.capture());
        assertThat(captor.getValue().getTopicArn()).isEqualTo(TOPIC_ARN);
        assertThat(gson.fromJson(captor.getValue().getMessage(), ChannelMessages.class)).isEqualTo(messages);
    }
}