package dev.number6.slack.adaptor;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.google.gson.Gson;
import dev.number6.slack.CallResponse;
import dev.number6.slack.port.HttpPort;
import dev.number6.slack.port.SlackPort;

import java.util.Optional;

public class SlackClientAdaptor implements SlackPort {

    public static final String CHANNEL_LIST_URL = "https://slack.com/api/channels.list";
    public static final String CHANNEL_HISTORY_URL = "https://slack.com/api/channels.history?count=1000&channel=%s&oldest=%s&latest=%s";
    public static final String JOIN_CHANNEL_URL = "https://slack.com/api/conversations.join?channel=%s";

    private final Gson gson = new Gson();

    private final HttpPort client;

    public SlackClientAdaptor(HttpPort client) {
        this.client = client;
    }

    @Override
    public Optional<String> getSlackResponse(String url, LambdaLogger logger, String body) {
        return getSlackResponse(url, body, null, logger);
    }

    @Override
    public <T> Optional<T> getSlackResponse(String url, Class<T> responseType, LambdaLogger logger) {
        return getSlackResponse(url, null, responseType, logger);
    }

    @Override
    public <T> Optional<T> getSlackResponse(String url, String body, Class<T> responseType, LambdaLogger logger) {
        CallResponse response = body == null ? client.get(url, logger) : client.post(url, body, logger);

        logger.log("Response from Slack request:" + response.body());
        return responseType == null || !response.isSuccess() ? Optional.empty() : Optional.of(gson.fromJson(response.body(), responseType));
    }
}
