package dev.number6.generate

import dev.number6.comprehend.results.PresentableEntityResults
import uk.org.fyodor.generators.Generator
import uk.org.fyodor.generators.RDG
import java.time.LocalDate

class PresentableEntityResultsGenerator : Generator<PresentableEntityResults> {
    var entityResultsGenerator = RDG.map(RDG.string(10),
            RDG.map(RDG.string(10), RDG.longVal(100)))

    override fun next(): PresentableEntityResults {
        return PresentableEntityResults(LocalDate.now(), entityResultsGenerator.next(), RDG.string().next())
    }
}