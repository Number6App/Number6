package dev.number6.slackreader.adaptor;

import dev.number6.slackreader.port.SlackReaderConfigurationPort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class EnvironmentVariableSlackReaderConfigurationAdapter implements SlackReaderConfigurationPort {

    private static final String SLACK_MSG_TOPIC_ARN = "SLACK_MSG_TOPIC_ARN";
    private static final String BLACKLISTED_CHANNELS = "BLACKLISTED_CHANNELS";

    private final String topicArn = System.getenv(SLACK_MSG_TOPIC_ARN);
    private final String blackListedChannels = System.getenv(BLACKLISTED_CHANNELS);

    @Override
    public String getTopicArn() {
        return topicArn;
    }

    @Override
    public Collection<String> getBlacklistedChannels() {
        return blackListedChannels == null ? new ArrayList<>() : Arrays.asList(blackListedChannels.split(","));
    }
}
