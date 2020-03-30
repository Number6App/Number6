package dev.number6.generate;

import com.amazonaws.services.comprehend.model.Entity;
import com.amazonaws.services.comprehend.model.EntityType;
import uk.org.fyodor.generators.Generator;
import uk.org.fyodor.range.Range;

public class EntityGenerator implements Generator<Entity> {


    @Override
    public Entity next() {
        return new Entity()
                .withText(CommonRDG.string(Range.closed(5, 25)).next())
                .withBeginOffset(CommonRDG.integer(Range.closed(1, 20)).next())
                .withEndOffset(CommonRDG.integer(Range.closed(20, 40)).next())
                .withScore(new Float(CommonRDG.doubleVal(1d).next()))
                .withType(CommonRDG.value(EntityType.class).next());
    }
}
