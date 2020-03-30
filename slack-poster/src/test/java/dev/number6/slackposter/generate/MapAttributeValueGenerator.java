package dev.number6.slackposter.generate;

import uk.org.fyodor.generators.Generator;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import static uk.org.fyodor.generators.RDG.map;

public class MapAttributeValueGenerator implements Generator<AttributeValue>{

    private final Generator<String> key;
    private final Generator<AttributeValue> val;
    private final int size;

    public MapAttributeValueGenerator(Generator<String> key, Generator<AttributeValue> val, int size) {
        this.key = key;
        this.val = val;
        this.size = size;
    }

    @Override
    public AttributeValue next() {
        return new AttributeValue().withM(map(key, val, size).next());
    }
}
