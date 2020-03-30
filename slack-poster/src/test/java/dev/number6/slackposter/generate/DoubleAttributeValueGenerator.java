package dev.number6.slackposter.generate;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import uk.org.fyodor.generators.Generator;
import uk.org.fyodor.generators.RDG;
import uk.org.fyodor.range.Range;

public class DoubleAttributeValueGenerator implements Generator<AttributeValue> {

    private final Range<Double> range;

    public DoubleAttributeValueGenerator(double max) {
        this.range = Range.closed(0d, max);
    }

    public DoubleAttributeValueGenerator(Range<Double> range) {
        this.range = range;
    }

    @Override
    public AttributeValue next() {
        return new AttributeValue().withN(RDG.doubleVal(range).next().toString());
    }
}
