package dev.number6.slack.model

import java.time.LocalDate

data class ChannelHistoryResponse(val ok: Boolean,
                                  val messages: Collection<Message> = listOf(),
                                  val has_more: Boolean = false,
                                  val oldest: Long = LocalDate.now().toEpochDay(),
                                  val latest: Long = LocalDate.now().toEpochDay()) {
    companion object {
        fun failed(): ChannelHistoryResponse {
            return ChannelHistoryResponse(false)
        }
    }
}