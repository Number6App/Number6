package dev.number6.slackposter

import com.amazonaws.services.dynamodbv2.model.AttributeValue
import dev.number6.slackposter.generate.DoubleAttributeValueGenerator
import dev.number6.slackposter.generate.IntegerAttributeValueGenerator
import dev.number6.slackposter.generate.MapAttributeValueGenerator
import uk.org.fyodor.generators.Generator
import uk.org.fyodor.generators.RDG
import uk.org.fyodor.range.Range

object SlackPosterRDG : RDG() {
    fun mapAttributeValues(key: Generator<String>, `val`: Generator<AttributeValue>, size: Int): Generator<AttributeValue> {
        return MapAttributeValueGenerator(key, `val`, size)
    }

    fun doubleAttributeValues(max: Double): Generator<AttributeValue> {
        return DoubleAttributeValueGenerator(max)
    }

    fun integerAttributeValues(max: Int): Generator<AttributeValue> {
        return IntegerAttributeValueGenerator(max)
    }
}