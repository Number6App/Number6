package dev.number6.generate

import dev.number6.comprehend.results.PresentableKeyPhrasesResults
import uk.org.fyodor.generators.Generator
import uk.org.fyodor.generators.RDG
import java.time.LocalDate

class PresentableKeyPhrasesResultsGenerator : Generator<PresentableKeyPhrasesResults> {
    var keyPhrasesResultsGenerator = RDG.map(RDG.string(10),
            RDG.longVal(100))

    override fun next(): PresentableKeyPhrasesResults {
        return PresentableKeyPhrasesResults(LocalDate.now(), keyPhrasesResultsGenerator.next(), RDG.string().next())
    }
}