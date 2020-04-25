package dev.number6

import dev.number6.slack.port.RequestPort
import org.assertj.core.api.AbstractAssert
import org.assertj.core.api.Assertions

class RequestPortAssert(callRequest: RequestPort) : AbstractAssert<RequestPortAssert, RequestPort>(callRequest, RequestPortAssert::class.java) {
    val isGetRequest: RequestPortAssert
        get() {
            Assertions.assertThat(actual.getMethod()).isEqualTo("GET")
            return this
        }

    val isPostRequest: RequestPortAssert
        get() {
            Assertions.assertThat(actual.getMethod()).isEqualTo("POST")
            return this
        }

    fun forUrl(url: String): RequestPortAssert {
        Assertions.assertThat(actual.getUrl()).isEqualTo(url)
        return this
    }

    fun withHeader(headerName: String, value: String): RequestPortAssert {
        Assertions.assertThat(actual.getHeader(headerName)).isEqualTo(value)
        return this
    }
}