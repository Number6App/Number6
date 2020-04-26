package dev.number6.slackreader.model

class JoinChannelResponse(val ok: Boolean) {

    override fun toString(): String {
        return "JoinChannelResponse{" +
                "ok=" + ok +
                '}'
    }

    companion object {
        fun failed(): JoinChannelResponse {
            return JoinChannelResponse(false)
        }

        fun ok(): JoinChannelResponse {
            return JoinChannelResponse(true)
        }
    }
}