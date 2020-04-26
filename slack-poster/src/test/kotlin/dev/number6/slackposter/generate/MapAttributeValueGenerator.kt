package dev.number6.slackposter.generate

import com.amazonaws.services.dynamodbv2.model.AttributeValue
import uk.org.fyodor.generators.Generator
import uk.org.fyodor.generators.RDG

class MapAttributeValueGenerator(private val key: Generator<String>, private val `val`: Generator<AttributeValue>, private val size: Int) : Generator<AttributeValue> {
    override fun next(): AttributeValue {
        return AttributeValue().withM(RDG.map(key, `val`, size).next())
    }

}