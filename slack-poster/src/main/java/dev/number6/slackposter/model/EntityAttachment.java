package dev.number6.slackposter.model;

import dev.number6.slackposter.ChannelSummaryImage;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EntityAttachment extends Attachment {

    public static final String CHANNEL_ENTITY_DETAILS = "{0} ({1})";

    EntityAttachment(ChannelSummaryImage image) {
        this.fallback = "Entities:";
        this.color = "warning";
        this.title = "Entities:";
        List<Field> fields = new ArrayList<>();
        image.getEntityTotals().forEach((k, v) -> {

            String title = asTitleCase(k) + ":";

            String entityLine = v.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue((o1, o2) -> o2 - o1))
                    .map(e -> MessageFormat.format(CHANNEL_ENTITY_DETAILS, e.getKey(), e.getValue()))
                    .collect(Collectors.joining(", "));

            fields.add(new Field(title, entityLine, false));
        });
        this.fields = fields.toArray(new Field[]{});
    }
}
