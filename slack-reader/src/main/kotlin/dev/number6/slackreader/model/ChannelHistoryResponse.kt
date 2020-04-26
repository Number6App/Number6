package dev.number6.slackreader.model

class ChannelHistoryResponse(val ok: Boolean, val messages: Collection<Message>, val has_more: Boolean,
                             val oldest: Long, val latest: Long) {

    override fun toString(): String {
        return "ChannelHistoryResponse{" +
                "ok=" + ok +
                ", messages=" + messages +
                '}'
    }

}