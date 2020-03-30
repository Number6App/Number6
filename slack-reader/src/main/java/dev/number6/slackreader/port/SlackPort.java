package dev.number6.slackreader.port;

import dev.number6.slackreader.model.Channel;
import dev.number6.slackreader.model.Message;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

import java.time.LocalDate;
import java.util.Collection;

public interface SlackPort {
    Collection<Channel> getChannelList(LambdaLogger logger);

    Collection<Message> getMessagesForChannelOnDate(Channel c, LocalDate date, LambdaLogger logger);
}
