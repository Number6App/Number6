package dev.number6.slackreader.model

class ChannelsListResponse(private val ok: Boolean?, private val channels: Collection<Channel>) {
    fun getOk(): Boolean? {
        return ok
    }

    fun getChannels(): Collection<Channel> {
        return channels
    }

    override fun toString(): String {
        return "ChannelsListResponse{" +
                "ok=" + ok +
                ", channels=" + channels +
                '}'
    }

}