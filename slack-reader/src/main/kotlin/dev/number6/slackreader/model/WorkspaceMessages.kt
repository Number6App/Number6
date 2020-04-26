package dev.number6.slackreader.model


import java.time.LocalDate
import java.util.*
import java.util.stream.Collectors
import kotlin.collections.ArrayList

class WorkspaceMessages @JvmOverloads constructor(comprehensionDate: LocalDate = LocalDate.now()) {
    val comprehensionDate: LocalDate
    private val channelMessages: MutableMap<String, Collection<String?>>
    fun add(channelName: String, messages: Collection<String?>): WorkspaceMessages {
        channelMessages[channelName] = messages
        return this
    }

//    fun getComprehensionDate(): LocalDate {
//        return comprehensionDate
//    }

    private fun getChannelNames(): Set<String> {
        return channelMessages.keys
    }

    fun getMessagesForChannel(channelName: String): Collection<String?> {
        return channelMessages[channelName] ?: ArrayList()
    }

    fun getActiveChannelNames(): Set<String> {
        return getChannelNames().stream().filter { c -> getMessagesForChannel(c).isNotEmpty() }.collect(Collectors.toSet())
    }

    init {
        channelMessages = HashMap()
        this.comprehensionDate = comprehensionDate
    }
}