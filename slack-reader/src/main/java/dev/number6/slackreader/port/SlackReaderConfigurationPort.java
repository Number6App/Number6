package dev.number6.slackreader.port;

import java.util.Collection;

public interface SlackReaderConfigurationPort {

    String getTopicArn();

    Collection<String> getBlacklistedChannels();
}
