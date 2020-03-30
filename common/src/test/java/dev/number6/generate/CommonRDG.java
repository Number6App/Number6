package dev.number6.generate;

import uk.org.fyodor.generators.RDG;

public class CommonRDG extends RDG {

    private final static PresentableEntityResultsGenerator presentableEntityResultsGenerator = new PresentableEntityResultsGenerator();
    private final static PresentableKeyPhrasesResultsGenerator presentableKeyPhrasesResultsGenerator = new PresentableKeyPhrasesResultsGenerator();
    private final static PresentableSentimentResultsGenerator presentableSentimentResultsGenerator = new PresentableSentimentResultsGenerator();
    private final static ChannelMessagesGenerator channelMessagesGenerator = new ChannelMessagesGenerator();
    private final static ChannelComprehensionSummaryGenerator channelComprehensionSummaryGenerator = new ChannelComprehensionSummaryGenerator();

    public static ChannelComprehensionSummaryGenerator channelComprehensionSummary() {
        return channelComprehensionSummaryGenerator;
    }

    public static PresentableEntityResultsGenerator presentableEntityResults() {
        return presentableEntityResultsGenerator;
    }

    public static PresentableSentimentResultsGenerator presentableSentimentResults() {
        return presentableSentimentResultsGenerator;
    }

    public static PresentableKeyPhrasesResultsGenerator presentableKeyPhrasesResults() {
        return presentableKeyPhrasesResultsGenerator;
    }

    public static DetectSentimentResultGenerator detectSentimentResult() {
        return new DetectSentimentResultGenerator();
    }

    public static ChannelMessagesGenerator channelMessages() {
        return channelMessagesGenerator;
    }

    public static float sentimentScoreFloat() {
        return doubleVal(1d).next().floatValue();
    }
}
