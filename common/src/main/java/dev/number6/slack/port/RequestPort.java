package dev.number6.slack.port;

public interface RequestPort {

    String getMethod();

    String getHeader(String headerName);

    String getUrl();

}
