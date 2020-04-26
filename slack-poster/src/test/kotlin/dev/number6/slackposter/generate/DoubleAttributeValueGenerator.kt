package dev.number6.slackposter.generate

import com.amazonaws.services.dynamodbv2.model.AttributeValue
import uk.org.fyodor.generators.Generator
import uk.org.fyodor.generators.RDG
import uk.org.fyodor.range.Range

class DoubleAttributeValueGenerator : Generator<AttributeValue> {
    private val range: Range<Double>

    constructor(max: Double) {
        range = Range.closed(0.0, max)
    }

    constructor(range: Range<Double>) {
        this.range = range
    }

    override fun next(): AttributeValue {
        return AttributeValue().withN(RDG.doubleVal(range).next().toString())
    }
}