package dev.number6.slackreader.model

class ChannelsListResponse(val ok: Boolean?, val channels: Collection<Channel>) {

    override fun toString(): String {
        return "ChannelsListResponse{" +
                "ok=" + ok +
                ", channels=" + channels +
                '}'
    }

}