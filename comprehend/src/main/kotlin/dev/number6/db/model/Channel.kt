package dev.number6.db.model

class Channel(val id: String, val name: String) {

    override fun toString(): String {
        return "Channel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}'
    }

}