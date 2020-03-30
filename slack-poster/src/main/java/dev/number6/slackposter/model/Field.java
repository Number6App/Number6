package dev.number6.slackposter.model;

import com.google.gson.annotations.SerializedName;

public class Field {
    private String title;
    private String value;
    @SerializedName("short")
    private Boolean shortField;

    public Field(String title, String value, Boolean shortField) {
        this.title = title;
        this.value = value;
        this.shortField = shortField;
    }

    public String getTitle() {
        return title;
    }

    public String getValue() {
        return value;
    }

    public Boolean getShortField() {
        return shortField;
    }

    @Override
    public String toString() {
        return "Field{" +
                "title='" + title + '\'' +
                ", value='" + value + '\'' +
                ", shortField=" + shortField +
                '}';
    }
}
