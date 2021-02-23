package dev.number6.comprehend

import com.amazonaws.services.comprehend.AmazonComprehend
import com.amazonaws.services.comprehend.model.*

class AwsComprehendClient(private val comprehendClient: AmazonComprehend) {
    fun getMessageSentiment(message: String?): DetectSentimentResult {
        val detectSentimentRequest = DetectSentimentRequest().withText(message).withLanguageCode(LANGUAGE_CODE)
        return comprehendClient.detectSentiment(detectSentimentRequest)
    }

    fun getMessageEntities(message: String?): DetectEntitiesResult {
        val detectSentimentRequest = DetectEntitiesRequest().withText(message).withLanguageCode(LANGUAGE_CODE)
        return comprehendClient.detectEntities(detectSentimentRequest)
    }

    fun getMessageKeyPhrases(message: String?): DetectKeyPhrasesResult {
        val detectKeyPhrasesRequest = DetectKeyPhrasesRequest().withText(message).withLanguageCode(LANGUAGE_CODE)
        return comprehendClient.detectKeyPhrases(detectKeyPhrasesRequest)
    }

    companion object {
        private const val LANGUAGE_CODE = "en"
    }
}