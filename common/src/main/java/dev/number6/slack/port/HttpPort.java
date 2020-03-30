package dev.number6.slack.port;

import dev.number6.slack.CallResponse;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

public interface HttpPort {

//    CallResponse makeHttpCall(T request);

    CallResponse get(String url, LambdaLogger logger);

    CallResponse post(String url, String body, LambdaLogger logger);
}
