package dev.number6.generate

import dev.number6.comprehend.results.PresentableKeyPhrasesResults
import uk.org.fyodor.generators.Generator
import uk.org.fyodor.generators.RDG
import java.time.LocalDate

class PresentableKeyPhrasesResultsGenerator : Generator<PresentableKeyPhrasesResults> {

    override fun next(): PresentableKeyPhrasesResults {
        return PresentableKeyPhrasesResults(LocalDate.now(), RDG.list(ComprehendRDG.detectKeyPhrasesResult()).next(), RDG.string().next())
    }
}