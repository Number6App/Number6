package dev.number6.db.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

@DynamoDBDocument
public class EntityTotal {

    private Long total;
    private String entity;
    private String type;

    public EntityTotal() {
    }

    public EntityTotal(Long total, String entity, String type) {
        this.total = total;
        this.entity = entity;
        this.type = type;
    }

    @DynamoDBAttribute
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @DynamoDBAttribute
    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    @DynamoDBAttribute
    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    @Override
    public String toString() {
        return "EntityTotal{" +
                "total=" + total +
                ", entity='" + entity + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
