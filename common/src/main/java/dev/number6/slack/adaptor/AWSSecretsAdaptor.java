package dev.number6.slack.adaptor;

import dev.number6.slack.port.SecretsConfigurationPort;
import dev.number6.slack.port.SecretsPort;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.model.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

public class AWSSecretsAdaptor implements SecretsPort {

    private final Gson gson = new Gson();
    private final AWSSecretsManager aws;
    private final SecretsConfigurationPort config;
    private String secret = null;

    public AWSSecretsAdaptor(AWSSecretsManager aws, SecretsConfigurationPort config) {
        this.aws = aws;
        this.config = config;
    }

    public String getSlackTokenSecret(LambdaLogger logger) {
        return retrieveSecretKeyValue(logger);
    }

    private String retrieveSecretKeyValue(LambdaLogger logger) {
        if (secret == null) {
            String secretName = config.getSlackTokenSecretName();
            GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest().withSecretId(secretName);
            GetSecretValueResult getSecretValueResult = null;
            try {
                getSecretValueResult = aws.getSecretValue(getSecretValueRequest);

            } catch (ResourceNotFoundException e) {
                logger.log("The requested secret " + secretName + " was not found");
            } catch (InvalidRequestException e) {
                logger.log("The request was invalid due to: " + e.getMessage());
            } catch (InvalidParameterException e) {
                logger.log("The request had invalid params: " + e.getMessage());
            }

            if (getSecretValueResult == null) {
                logger.log("No secret found, returning empty string");
                return "";
            }

            // Decrypted secret using the associated KMS CMK
            // Depending on whether the secret was a string or binary, one of these fields will be populated
            if (getSecretValueResult.getSecretString() != null) {
                secret = getSecretValueResult.getSecretString();
            } else {
                secret = getSecretValueResult.getSecretBinary().toString();
            }
        }
        return secret;
    }
}
