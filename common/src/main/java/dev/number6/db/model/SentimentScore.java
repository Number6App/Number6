package dev.number6.db.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

@DynamoDBDocument
public class SentimentScore {

    private float total;
    private String sentiment;

    public SentimentScore() {
    }

    public SentimentScore(float total, String sentiment) {
        this.total = total;
        this.sentiment = sentiment;
    }

    @DynamoDBAttribute
    public Float getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @DynamoDBAttribute
    public String getSentiment() {
        return sentiment;
    }

    public void setSentiment(String sentiment) {
        this.sentiment = sentiment;
    }

    @Override
    public String toString() {
        return "SentimentScore{" +
                "total=" + total +
                ", sentiment='" + sentiment + '\'' +
                '}';
    }
}
