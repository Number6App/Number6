package dev.number6.slackposter.model;

import dev.number6.slackposter.ChannelSummaryImage;

import java.text.MessageFormat;

public class PresentableChannelSummary {

    //\u2022 == bullet point character
    public static final String CHANNEL_MESSAGE_TOTAL_FORMAT = "\u2022 *{0}*: {1} messages on {2}";

    private final ChannelSummaryImage image;

    public PresentableChannelSummary(ChannelSummaryImage image) {
        this.image = image;
    }

    public String getInitialMessageLine() {

        return MessageFormat.format(CHANNEL_MESSAGE_TOTAL_FORMAT,
                image.getChannelName(),
                image.getSentimentTotals().values().stream().reduce(0, Integer::sum),
                image.getComprehensionDate());
    }

    public Attachment[] getAttachments() {
        return new Attachment[]{getOverallSentimentAttachment(), getEntityAttachment(), getKeyPhrasesAttachment()};
    }

    private Attachment getOverallSentimentAttachment() {
        return new SentimentAttachment(image);
    }

    private Attachment getKeyPhrasesAttachment() {
        return new KeyPhrasesAttachment(image);
    }

    private Attachment getEntityAttachment() {
        return new EntityAttachment(image);
    }
}
