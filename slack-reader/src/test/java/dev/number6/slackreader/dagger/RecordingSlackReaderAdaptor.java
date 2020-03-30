package dev.number6.slackreader.dagger;

import dev.number6.slack.port.HttpPort;
import dev.number6.slackreader.adaptor.SlackReaderAdaptor;
import dev.number6.slackreader.model.Channel;
import dev.number6.slackreader.model.Message;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class RecordingSlackReaderAdaptor extends SlackReaderAdaptor {

    private Collection<String> channelNames = new ArrayList<>();
    private Map<String, List<String>> channelMessages = new HashMap<>();

    public RecordingSlackReaderAdaptor(HttpPort client) {
        super(client);
    }

    @Override
    public Collection<Channel> getChannelList(LambdaLogger logger) {
        Collection<Channel> channels = super.getChannelList(logger);
        channelNames = channels.stream().map(Channel::getName).collect(Collectors.toList());
        return channels;
    }

    @Override
    public Collection<Message> getMessagesForChannelOnDate(Channel c, LocalDate date, LambdaLogger logger) {
        Collection<Message> messages = super.getMessagesForChannelOnDate(c, date, logger);
        channelMessages.put(c.getName(), messages.stream().map(Message::getText).collect(Collectors.toList()));
        return messages;
    }

    public List<String> getMessagesForChannelName(String channelName) {
        return channelMessages.getOrDefault(channelName, new ArrayList<>());
    }

    public Collection<String> getChannelNames() {
        return channelNames;
    }
}
