package dev.number6.slackreader.dagger;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.google.gson.Gson;
import dev.number6.slack.CallResponse;
import dev.number6.slack.adaptor.SlackClientAdaptor;
import dev.number6.slack.port.HttpPort;
import dev.number6.slackreader.generate.SlackReaderRDG;
import dev.number6.slackreader.model.JoinChannelResponse;
import uk.org.fyodor.generators.Generator;

import java.util.HashMap;
import java.util.Map;

public class FakeHttpAdaptor implements HttpPort {

    private final Gson gson = new Gson();
    private final Map<String, Generator<String>> response;

    public FakeHttpAdaptor() {
        response = new HashMap<>();
        response.put(SlackClientAdaptor.CHANNEL_LIST_URL, SlackReaderRDG.jsonChannelsListResponse());
        response.put(urlBeforeParameters(SlackClientAdaptor.CHANNEL_HISTORY_URL), SlackReaderRDG.jsonChannelHistoryResponse());
        response.put(urlBeforeParameters(SlackClientAdaptor.JOIN_CHANNEL_URL), () -> gson.toJson(JoinChannelResponse.ok()));
    }

    @Override
    public CallResponse get(String url, LambdaLogger logger) {
        String next = response.get(urlBeforeParameters(url)).next();
        return new CallResponse(next);
    }

    @Override
    public CallResponse post(String url, String body, LambdaLogger logger) {
        return new CallResponse(response.get(urlBeforeParameters(url)).next());
    }

    private String urlBeforeParameters(String url) {
        return url.split("\\?")[0];
    }

}
