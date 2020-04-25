package dev.number6.slack

import dev.number6.slack.port.RequestPort
import okhttp3.Request

class OkHttpRequestAdaptor(private val request: Request) : RequestPort {
    fun valueOf(): Request {
        return request
    }

    override fun getMethod(): String {
        return request.method()
    }

    override fun getHeader(headerName: String): String? {
        return request.header(headerName)
    }

    override fun getUrl(): String {
        return request.url().toString()
    }
}