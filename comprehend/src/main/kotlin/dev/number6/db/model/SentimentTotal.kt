package dev.number6.db.model

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument

@DynamoDBDocument
class SentimentTotal {
    @DynamoDBAttribute
    var total = 0

    @DynamoDBAttribute
    var sentiment: String? = null

    constructor() {}
    constructor(total: Int, sentiment: String?) {
        this.total = total
        this.sentiment = sentiment
    }

    override fun toString(): String {
        return "SentimentTotal{" +
                "total=" + total +
                ", sentiment='" + sentiment + '\'' +
                '}'
    }
}