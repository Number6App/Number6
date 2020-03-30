package dev.number6.slackposter;

import dev.number6.slackposter.generate.DoubleAttributeValueGenerator;
import dev.number6.slackposter.generate.IntegerAttributeValueGenerator;
import dev.number6.slackposter.generate.MapAttributeValueGenerator;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import uk.org.fyodor.generators.Generator;
import uk.org.fyodor.generators.RDG;
import uk.org.fyodor.range.Range;

public class SlackPosterRDG extends RDG {

    public static Generator<AttributeValue> mapAttributeValues(Generator<String> key, Generator<AttributeValue> val, int size) {
        return new MapAttributeValueGenerator(key, val, size);
    }

    public static Generator<AttributeValue> doubleAttributeValues(double max) {
        return new DoubleAttributeValueGenerator(max);
    }

    public static Generator<AttributeValue> doubleAttributeValues(Range<Double> range) {
        return new DoubleAttributeValueGenerator(range);
    }

    public static Generator<AttributeValue> integerAttributeValues(int max) {
        return new IntegerAttributeValueGenerator(max);
    }

    public static Generator<AttributeValue> integerAttributeValues(Range<Integer> range) {
        return new IntegerAttributeValueGenerator(range);
    }

}
