package dev.number6.generate

import com.amazonaws.services.comprehend.model.DetectEntitiesResult
import uk.org.fyodor.generators.Generator
import uk.org.fyodor.generators.RDG

class DetectEntitiesResultGenerator : Generator<DetectEntitiesResult> {
    override fun next(): DetectEntitiesResult {
        return DetectEntitiesResult()
                .withEntities(RDG.list(ComprehendRDG.entity()).next())

    }
}
