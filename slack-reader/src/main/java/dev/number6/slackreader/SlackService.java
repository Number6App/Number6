package dev.number6.slackreader;

import dev.number6.slackreader.model.Message;
import dev.number6.slackreader.model.WorkspaceMessages;
import dev.number6.slackreader.port.SlackPort;
import dev.number6.slackreader.port.SlackReaderConfigurationPort;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Collectors;

public class SlackService {
    private final SlackPort slackPort;
    private final SlackReaderConfigurationPort config;

    public SlackService(SlackPort slackPort, SlackReaderConfigurationPort config) {

        this.slackPort = slackPort;
        this.config = config;
    }

    public WorkspaceMessages getMessagesOnDate(LocalDate comprehensionDate, LambdaLogger logger) {
        Collection<String> blacklistedChannels = config.getBlacklistedChannels();

        return slackPort.getChannelList(logger).stream()
                .filter(c -> !blacklistedChannels.contains(c.getName()))
                .reduce(new WorkspaceMessages(comprehensionDate),
                        (wm, c) -> {
                            wm.add(c.getName(),
                                    slackPort.getMessagesForChannelOnDate(c, comprehensionDate, logger)
                                            .stream()
                                            .map(Message::getText)
                                            .collect(Collectors.toList()));
                            return wm;
                        },
                        (wm1, wm2) -> wm2);
    }
}
