package dev.number6.slackposter.model

import com.google.gson.annotations.SerializedName

class Field(val title: String, val value: String, @field:SerializedName("short") val shortField: Boolean) {

    override fun toString(): String {
        return "Field{" +
                "title='" + title + '\'' +
                ", value='" + value + '\'' +
                ", shortField=" + shortField +
                '}'
    }

}