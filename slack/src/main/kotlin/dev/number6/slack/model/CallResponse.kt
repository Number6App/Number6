package dev.number6.slack.model

internal class CallResponse {
    val isSuccess: Boolean
    private val value: String

    constructor(value: String) {
        isSuccess = true
        this.value = value
    }

    constructor(e: Exception) {
        isSuccess = false
        value = e.message ?: "No Exception Message."
    }

    fun body(): String {
        return value
    }

}