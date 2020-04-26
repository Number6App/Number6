package dev.number6.slackreader.model


import java.util.*

class Message @JvmOverloads constructor(val type: String,
                                        val text: String?,
                                        private val subtype: String? = null) {

    private fun getSubtype(): Optional<String> {
        return Optional.ofNullable(subtype)
    }

    fun isBotMessage(): Boolean {
        return getSubtype().orElse("").equals(BOT_MESSAGE_SUBTYPE, ignoreCase = true)
    }

    override fun toString(): String {
        return "Message{" +
                "type='" + type + '\'' +
                ", subtype='" + subtype + '\'' +
                ", text='" + text + '\'' +
                '}'
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val message = o as Message
        return type == message.type &&
                subtype == message.subtype && text == message.text
    }

    override fun hashCode(): Int {
        return Objects.hash(type, subtype, text)
    }

    companion object {
        const val BOT_MESSAGE_SUBTYPE = "bot_message"

        fun fromBot(message: Message): Message {
            return Message(message.type, message.text, BOT_MESSAGE_SUBTYPE)
        }

        fun empty(message: Message): Message {
            return Message(message.type, "")
        }

        fun nullText(message: Message): Message {
            return Message(message.type, null)
        }

        fun spaceText(message: Message): Message? {
            return Message(message.type, "        ")
        }
    }

}