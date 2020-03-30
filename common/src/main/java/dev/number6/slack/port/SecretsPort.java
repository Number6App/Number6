package dev.number6.slack.port;

import com.amazonaws.services.lambda.runtime.LambdaLogger;

public interface SecretsPort {

    String getSlackTokenSecret(LambdaLogger logger);
}
