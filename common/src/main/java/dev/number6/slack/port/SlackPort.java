package dev.number6.slack.port;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import okhttp3.RequestBody;

import java.util.Optional;

public interface SlackPort {

    Optional<String> getSlackResponse(String url, LambdaLogger logger, String body);

    <T> Optional<T> getSlackResponse(String url, Class<T> responseType, LambdaLogger logger);

    <T> Optional<T> getSlackResponse(String url, String body, Class<T> responseType, LambdaLogger logger);
}
