package dev.number6.db.model;

import dev.number6.db.model.converter.LocalDateConverter;
import dev.number6.db.model.converter.LocalDateTimeConverter;
import com.amazonaws.services.dynamodbv2.datamodeling.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")//used by DynamoDB
@DynamoDBTable(tableName = "SlackComprehension")
public class ChannelComprehensionSummary {

    private LocalDateTime createDate = LocalDateTime.now();
    private LocalDate comprehensionDate;
    private String channelName;
    private Map<String, Float> sentimentScoreTotals = new HashMap<>();
    private Map<String, Integer> sentimentTotals = new HashMap<>();
    private Map<String, Long> keyPhrasesTotals = new HashMap<>();
    private Map<String, Map<String, Long>> entityTotals = new HashMap<>();
    private Integer version;

    public ChannelComprehensionSummary() {
    }

    public ChannelComprehensionSummary(String channelName, LocalDate comprehensionDate) {
        this.channelName = channelName;
        this.comprehensionDate = comprehensionDate;
    }

    @DynamoDBHashKey
    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    @DynamoDBRangeKey
    @DynamoDBTypeConverted(converter = LocalDateConverter.class)
    public LocalDate getComprehensionDate() {
        return comprehensionDate;
    }

    public void setComprehensionDate(LocalDate comprehensionDate) {
        this.comprehensionDate = comprehensionDate;
    }

    @DynamoDBAttribute
//    @DynamoDBTypeConverted(converter = SentimentScoreConverter.class)
    public Map<String, Float> getSentimentScoreTotals() {
        return sentimentScoreTotals;
    }

    public void setSentimentScoreTotals(Map<String, Float> sentimentScoreTotals) {
        this.sentimentScoreTotals = sentimentScoreTotals;
    }

    @DynamoDBAttribute
//    @DynamoDBTypeConverted(converter = SentimentTotalConverter.class)
    public Map<String,Integer> getSentimentTotals() {
        return sentimentTotals;
    }

    public void setSentimentTotals(Map<String, Integer> sentimentTotals) {
        this.sentimentTotals = sentimentTotals;
    }

    @DynamoDBAttribute
//    @DynamoDBTypeConverted(converter = EntityTotalConverter.class)
    public Map<String, Map<String, Long>> getEntityTotals() {
        return entityTotals;
    }

    public void setEntityTotals(Map<String, Map<String, Long>> entityTotals) {
        this.entityTotals = entityTotals;
    }

    @DynamoDBAttribute
    public Map<String, Long> getKeyPhrasesTotals() {
        return keyPhrasesTotals;
    }

    public void setKeyPhrasesTotals(Map<String, Long> keyPhrasesTotals) {
        this.keyPhrasesTotals = keyPhrasesTotals;
    }

    @DynamoDBAttribute
    @DynamoDBTypeConverted(converter = LocalDateTimeConverter.class)
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    @DynamoDBVersionAttribute
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
