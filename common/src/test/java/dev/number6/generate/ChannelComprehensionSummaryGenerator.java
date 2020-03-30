package dev.number6.generate;


import dev.number6.db.model.ChannelComprehensionSummary;
import uk.org.fyodor.generators.Generator;

import java.time.LocalDate;

public class ChannelComprehensionSummaryGenerator implements Generator<ChannelComprehensionSummary> {

    @Override
    public ChannelComprehensionSummary next() {
        return new ChannelComprehensionSummary(CommonRDG.string().next(), LocalDate.now());
    }
}