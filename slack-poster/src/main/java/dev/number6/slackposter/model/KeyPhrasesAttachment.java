package dev.number6.slackposter.model;

import dev.number6.slackposter.ChannelSummaryImage;

import java.text.MessageFormat;
import java.util.Map;
import java.util.stream.Collectors;

public class KeyPhrasesAttachment extends Attachment {

    public static final String CHANNEL_KEY_PHRASES_DETAILS = "{0} ({1})";

    KeyPhrasesAttachment(ChannelSummaryImage image) {
        this.fallback = "Key Phrases (occurring more than once)";
        this.color = "danger";
        this.title = "Key Phrases (occurring more than once):";

        Map<String, Integer> keyPhrases = image.getKeyPhrasesTotals();
        String keyPhrasesLine = keyPhrases.entrySet().stream()
                .filter(e -> e.getValue() > 1)
                .sorted(Map.Entry.comparingByValue((i1, i2) -> i2 - i1))
                .map(e -> MessageFormat.format(CHANNEL_KEY_PHRASES_DETAILS, e.getKey(), e.getValue()))
                .collect(Collectors.joining(", "));

        this.fields = new Field[]{new Field("", keyPhrasesLine, false)};
    }
}
