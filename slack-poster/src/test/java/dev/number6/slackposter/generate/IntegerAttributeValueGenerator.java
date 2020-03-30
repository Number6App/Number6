package dev.number6.slackposter.generate;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import uk.org.fyodor.generators.Generator;
import uk.org.fyodor.generators.RDG;
import uk.org.fyodor.range.Range;

public class IntegerAttributeValueGenerator implements Generator<AttributeValue> {

    private final Range<Integer> range;

    public IntegerAttributeValueGenerator(int max) {
        this.range = Range.closed(0, max);
    }

    public IntegerAttributeValueGenerator(Range<Integer> range) {
        this.range = range;
    }

    @Override
    public AttributeValue next() {
        return new AttributeValue().withN(RDG.integer(range).next().toString());
    }
}
