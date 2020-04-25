package dev.number6

import dev.number6.slack.OkHttpRequestAdaptor
import org.assertj.core.api.Assertions

object SlackAssertions : Assertions() {
    fun assertThat(callRequest: OkHttpRequestAdaptor): RequestPortAssert {
        return RequestPortAssert(callRequest)
    }
}