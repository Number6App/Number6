package dev.number6.db.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

@DynamoDBDocument
public class SentimentTotal {

    private int total;
    private String sentiment;

    public SentimentTotal() {
    }

    public SentimentTotal(int total, String sentiment) {
        this.total = total;
        this.sentiment = sentiment;
    }

    @DynamoDBAttribute
    public Integer getTotal() {
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
        return "SentimentTotal{" +
                "total=" + total +
                ", sentiment='" + sentiment + '\'' +
                '}';
    }
}
