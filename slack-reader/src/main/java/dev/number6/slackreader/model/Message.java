package dev.number6.slackreader.model;

import java.util.Objects;
import java.util.Optional;

public class Message {

    public static final String BOT_MESSAGE_SUBTYPE = "bot_message";
    private final String type;
    private final String subtype;
    private final String text;

    public Message(String type, String text) {
        this(type, text, null);
    }

    public Message(String type, String text, String subtype) {
        this.text = text;
        this.type = type;
        this.subtype = subtype;
    }

    public static Message fromBot(Message message) {
        return new Message(message.getType(), message.getText(), BOT_MESSAGE_SUBTYPE);
    }

    public static Message empty(Message message) {
        return new Message(message.getType(), "");
    }

    public static Message nullText(Message message) {
        return new Message(message.getType(), null);
    }

    public static Message spaceText(Message message) {
        return new Message(message.getType(), "        ");
    }

    public String getType() {
        return type;
    }

    public String getText() {
        return text;
    }

    public Optional<String> getSubtype() {
        return Optional.ofNullable(subtype);
    }

    public boolean isBotMessage() {
        return getSubtype().orElse("").equalsIgnoreCase(Message.BOT_MESSAGE_SUBTYPE);
    }

    @Override
    public String toString() {
        return "Message{" +
                "type='" + type + '\'' +
                ", subtype='" + subtype + '\'' +
                ", text='" + text + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return type.equals(message.type) &&
                Objects.equals(subtype, message.subtype) &&
                text.equals(message.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, subtype, text);
    }
}
