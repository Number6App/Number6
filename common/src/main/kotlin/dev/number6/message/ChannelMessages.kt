package dev.number6.message

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate
import java.util.*

class ChannelMessages {
    @JsonProperty
    lateinit var comprehensionDate: LocalDate
        private set

    @JsonProperty
    lateinit var channelName: String
        private set

    @JsonProperty
    lateinit var messages: Collection<String?>
        private set

    constructor() {}
    constructor(channelName: String, messages: Collection<String?>, comprehensionDate: LocalDate) {
        this.channelName = channelName
        this.messages = messages
        this.comprehensionDate = comprehensionDate
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as ChannelMessages
        return comprehensionDate == that.comprehensionDate &&
                channelName == that.channelName &&
                messages == that.messages
    }

    override fun hashCode(): Int {
        return Objects.hash(comprehensionDate, channelName, messages)
    }
}