package dev.number6.slack.port

interface RequestPort {
    fun getMethod(): String
    fun getHeader(headerName: String): String?
    fun getUrl(): String
}