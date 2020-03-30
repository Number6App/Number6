package dev.number6.slackposter.dagger;

import dev.number6.slack.port.RequestPort;

public class FakeRequestPort implements RequestPort {
    @Override
    public String getMethod() {
        return null;
    }

    @Override
    public String getHeader(String headerName) {
        return null;
    }

    @Override
    public String getUrl() {
        return null;
    }
}
