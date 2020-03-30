package dev.number6.slackreader.adaptor;

import dev.number6.slackreader.port.SlackReaderConfigurationPort;
import dev.number6.slackreader.port.NotificationPort;
import dev.number6.message.ChannelMessages;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishRequest;
import com.google.gson.Gson;

public class SnsNotificationAdaptor implements NotificationPort {

    private final Gson gson = new Gson();
    private final AmazonSNS sns;
    private final String topicArn;

    public SnsNotificationAdaptor(SlackReaderConfigurationPort config, AmazonSNS sns) {
        this.topicArn = config.getTopicArn();
        this.sns = sns;
    }

    @Override
    public void broadcast(ChannelMessages messages) {
        sns.publish(new PublishRequest(topicArn, gson.toJson(messages)));
    }
}
