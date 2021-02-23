package dev.number6.slack.model

data class ChannelsListResponse(val ok: Boolean,
                                val channels: Collection<Channel> = listOf()) {
    companion object {
        fun failed(): ChannelsListResponse {
            return ChannelsListResponse(false)
        }
    }
}