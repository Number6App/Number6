package dev.number6.slackposter.generate

import com.amazonaws.services.dynamodbv2.model.AttributeValue
import uk.org.fyodor.generators.Generator
import uk.org.fyodor.generators.RDG
import uk.org.fyodor.range.Range

class IntegerAttributeValueGenerator : Generator<AttributeValue> {
    private val range: Range<Int>

    constructor(max: Int) {
        range = Range.closed(0, max)
    }

    constructor(range: Range<Int>) {
        this.range = range
    }

    override fun next(): AttributeValue {
        return AttributeValue().withN(RDG.integer(range).next().toString())
    }
}