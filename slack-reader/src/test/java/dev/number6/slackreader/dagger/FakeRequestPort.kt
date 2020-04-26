package dev.number6.slackreader.dagger

import dev.number6.slack.port.RequestPort

class FakeRequestPort : RequestPort {
    override fun getMethod(): String {
        return "null"
    }

    override fun getHeader(headerName: String): String {
        return "null"
    }

    override fun getUrl(): String {
        return "null"
    }
}