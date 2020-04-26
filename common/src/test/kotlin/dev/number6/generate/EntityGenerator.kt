package dev.number6.generate

import com.amazonaws.services.comprehend.model.Entity
import com.amazonaws.services.comprehend.model.EntityType
import uk.org.fyodor.generators.Generator
import uk.org.fyodor.generators.RDG
import uk.org.fyodor.range.Range

class EntityGenerator : Generator<Entity> {
    override fun next(): Entity {
        return Entity()
                .withText(RDG.string(Range.closed(5, 25)).next())
                .withBeginOffset(RDG.integer(Range.closed(1, 20)).next())
                .withEndOffset(RDG.integer(Range.closed(20, 40)).next())
                .withScore(RDG.doubleVal(1.0).next().toFloat())
                .withType(RDG.value(EntityType::class.java).next())
    }
}