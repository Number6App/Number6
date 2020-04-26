package dev.number6.db.model

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument

@DynamoDBDocument
class EntityTotal {
    @DynamoDBAttribute
    var total: Long? = null

    @DynamoDBAttribute
    var entity: String? = null

    @DynamoDBAttribute
    var type: String? = null

    constructor() {}
    constructor(total: Long?, entity: String?, type: String?) {
        this.total = total
        this.entity = entity
        this.type = type
    }

    override fun toString(): String {
        return "EntityTotal{" +
                "total=" + total +
                ", entity='" + entity + '\'' +
                ", type='" + type + '\'' +
                '}'
    }
}