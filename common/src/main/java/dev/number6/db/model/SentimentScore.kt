package dev.number6.db.model

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument

@DynamoDBDocument
class SentimentScore {
    @DynamoDBAttribute
    var total = 0f
        private set

    @DynamoDBAttribute
    var sentiment: String? = null

    constructor() {}
    constructor(total: Float, sentiment: String?) {
        this.total = total
        this.sentiment = sentiment
    }

    fun setTotal(total: Int) {
        this.total = total.toFloat()
    }

    override fun toString(): String {
        return "SentimentScore{" +
                "total=" + total +
                ", sentiment='" + sentiment + '\'' +
                '}'
    }
}