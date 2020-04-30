package dev.number6.slack.model

import java.util.*

class Channel(val id: String, val name: String) {

    override fun toString(): String {
        return "Channel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}'
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val channel = other as Channel
        return id == channel.id &&
                name == channel.name
    }

    override fun hashCode(): Int {
        return Objects.hash(id, name)
    }

}