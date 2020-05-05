package dev.number6.generate

import dev.number6.comprehend.results.PresentableEntityResults
import uk.org.fyodor.generators.Generator
import uk.org.fyodor.generators.RDG
import java.time.LocalDate

class PresentableEntityResultsGenerator : Generator<PresentableEntityResults> {

    override fun next(): PresentableEntityResults {
        return PresentableEntityResults(LocalDate.now(), RDG.list(ComprehendRDG.detectEntitiesResult()).next(), RDG.string().next())
    }
}