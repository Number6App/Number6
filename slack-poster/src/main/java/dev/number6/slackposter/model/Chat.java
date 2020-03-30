package dev.number6.slackposter.model;

public class Chat {
    public final String channel;
    public final String text;
    public final Attachment[] attachments;

    public Chat(String channelId, String message, Attachment... attachments) {

        this.channel = channelId;
        this.text = message;
        this.attachments = attachments;
    }
}
