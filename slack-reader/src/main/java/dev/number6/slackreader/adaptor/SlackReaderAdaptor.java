package dev.number6.slackreader.adaptor;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import dev.number6.slack.adaptor.SlackClientAdaptor;
import dev.number6.slack.port.HttpPort;
import dev.number6.slackreader.model.*;
import dev.number6.slackreader.port.SlackPort;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class SlackReaderAdaptor extends SlackClientAdaptor implements SlackPort {

    public SlackReaderAdaptor(HttpPort client) {
        super(client);
    }

    @Override
    public Collection<Channel> getChannelList(LambdaLogger logger) {
        Optional<ChannelsListResponse> response = getSlackResponse(CHANNEL_LIST_URL,
                ChannelsListResponse.class,
                logger);

        return response.isPresent() ? response.get().getChannels() : new ArrayList<>();
    }

    @Override
    public Collection<Message> getMessagesForChannelOnDate(Channel c, LocalDate date, LambdaLogger logger) {
        Optional<JoinChannelResponse> join = getSlackResponse(String.format(JOIN_CHANNEL_URL, c.getId()), "", JoinChannelResponse.class, logger);
        if (join.orElse(JoinChannelResponse.failed()).getOk()) {
            Optional<ChannelHistoryResponse> response = getSlackResponse(String.format(CHANNEL_HISTORY_URL,
                    c.getId(),
                    date.atStartOfDay().toEpochSecond(ZoneOffset.UTC),
                    date.plusDays(1).atStartOfDay().toEpochSecond(ZoneOffset.UTC)),
                    ChannelHistoryResponse.class,
                    logger);

            return response.map(chr -> chr.getMessages().stream()
                    .filter(m -> m.getText() != null && !m.getText().isEmpty())
                    .filter(m -> !m.isBotMessage())
                    .collect(Collectors.toList()))
                    .orElseGet(ArrayList::new);
        } else {
            logger.log("Error trying to join channel " + c);
            return new ArrayList<>();
        }
    }
}
