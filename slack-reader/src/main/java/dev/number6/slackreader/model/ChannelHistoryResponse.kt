package dev.number6.slackreader.model

class ChannelHistoryResponse(private val ok: Boolean, private val messages: Collection<Message>, private val has_more: Boolean, private val oldest: Long, private val latest: Long) {
    fun getOk(): Boolean? {
        return ok
    }

    fun getMessages(): Collection<Message> {
        return messages
    }

    fun getHasMore(): Boolean? {
        return has_more
    }

    fun getOldest(): Long? {
        return oldest
    }

    fun getLatest(): Long? {
        return latest
    }

    override fun toString(): String {
        return "ChannelHistoryResponse{" +
                "ok=" + ok +
                ", messages=" + messages +
                '}'
    }

}