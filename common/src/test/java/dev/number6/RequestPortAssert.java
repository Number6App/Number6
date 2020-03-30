package dev.number6;

import dev.number6.slack.port.RequestPort;
import org.assertj.core.api.AbstractAssert;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestPortAssert extends AbstractAssert<RequestPortAssert, RequestPort> {

    public RequestPortAssert(RequestPort callRequest) {
        super(callRequest, RequestPortAssert.class);
    }

    public RequestPortAssert isGetRequest() {
        assertThat(actual.getMethod()).isEqualTo("GET");
        return this;
    }

    public RequestPortAssert isPostRequest() {
        assertThat(actual.getMethod()).isEqualTo("POST");
        return this;
    }

    public RequestPortAssert forUrl(String url) {
        assertThat(actual.getUrl()).isEqualTo(url);
        return this;
    }

    public RequestPortAssert withHeader(String headerName, String value) {
        assertThat(actual.getHeader(headerName)).isEqualTo(value);
        return this;
    }
}
