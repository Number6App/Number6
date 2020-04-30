package dev.number6.slack.model

data class ChannelHistoryResponse(val ok: Boolean,
                                  val messages: Collection<Message>,
                                  val has_more: Boolean,
                                  val oldest: Long,
                                  val latest: Long)