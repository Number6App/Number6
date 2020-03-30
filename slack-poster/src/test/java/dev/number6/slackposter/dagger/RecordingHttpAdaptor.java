package dev.number6.slackposter.dagger;

import dev.number6.slack.CallResponse;
import dev.number6.slack.port.HttpPort;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

import java.util.HashMap;
import java.util.Map;

public class RecordingHttpAdaptor implements HttpPort {

    private Map<String, String> posts = new HashMap<>();

    @Override
    public CallResponse get(String url, LambdaLogger logger) {
        return new CallResponse("quack");
    }

    @Override
    public CallResponse post(String url, String body, LambdaLogger logger) {

        posts.put(url, body);
        return new CallResponse("quack");
    }

    public Map<String, String> getPosts() {
        return posts;
    }
}
