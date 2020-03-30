package dev.number6.slack.adaptor;

import dev.number6.slack.port.SecretsConfigurationPort;

public class EnvironmentVariableSecretsConfigurationAdaptor implements SecretsConfigurationPort {

    private static final String SLACK_TOKEN_SECRET_NAME = "SLACK_TOKEN_SECRET_NAME";

    private final String secretName = System.getenv(SLACK_TOKEN_SECRET_NAME);

    @Override
    public String getSlackTokenSecretName() {
        return secretName;
    }
}
