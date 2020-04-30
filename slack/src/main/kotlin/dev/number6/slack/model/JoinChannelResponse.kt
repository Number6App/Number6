package dev.number6.slack.model

data class JoinChannelResponse(val ok: Boolean) {

    companion object {
        fun failed(): JoinChannelResponse {
            return JoinChannelResponse(false)
        }

        fun ok(): JoinChannelResponse {
            return JoinChannelResponse(true)
        }
    }
}