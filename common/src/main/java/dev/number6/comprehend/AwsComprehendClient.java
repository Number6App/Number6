package dev.number6.comprehend;

import com.amazonaws.services.comprehend.AmazonComprehend;
import com.amazonaws.services.comprehend.model.*;

public class AwsComprehendClient {

    private static final String LANGUAGE_CODE = "en";
    private final AmazonComprehend comprehendClient;

    public AwsComprehendClient(AmazonComprehend amazonComprehend) {
        this.comprehendClient = amazonComprehend;
    }

    public DetectSentimentResult getMessageSentiment(String message) {
        DetectSentimentRequest detectSentimentRequest = new DetectSentimentRequest().withText(message).withLanguageCode(LANGUAGE_CODE);
        return comprehendClient.detectSentiment(detectSentimentRequest);
    }

    public DetectEntitiesResult getMessageEntities(String message) {
        DetectEntitiesRequest detectSentimentRequest = new DetectEntitiesRequest().withText(message).withLanguageCode(LANGUAGE_CODE);
        return comprehendClient.detectEntities(detectSentimentRequest);
    }

    public DetectKeyPhrasesResult getMessageKeyPhrases(String message) {
        DetectKeyPhrasesRequest detectKeyPhrasesRequest = new DetectKeyPhrasesRequest().withText(message).withLanguageCode(LANGUAGE_CODE);
        return comprehendClient.detectKeyPhrases(detectKeyPhrasesRequest);
    }
}
