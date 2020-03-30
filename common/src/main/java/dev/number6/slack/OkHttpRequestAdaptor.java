package dev.number6.slack;

import dev.number6.slack.port.RequestPort;
import okhttp3.Request;

public class OkHttpRequestAdaptor implements RequestPort {

    private Request request;

    public OkHttpRequestAdaptor(Request request) {
        this.request = request;
    }

    public Request valueOf() {
        return request;
    }

    @Override
    public String getMethod() {
        return request.method();
    }

    @Override
    public String getHeader(String headerName) {
        return request.header(headerName);
    }

    @Override
    public String getUrl() {
        return request.url().toString();
    }
}
