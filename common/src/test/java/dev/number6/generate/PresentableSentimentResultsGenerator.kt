package dev.number6.generate

import dev.number6.comprehend.results.PresentableSentimentResults
import uk.org.fyodor.generators.Generator
import uk.org.fyodor.generators.RDG
import uk.org.fyodor.range.Range
import java.time.LocalDate

class PresentableSentimentResultsGenerator : Generator<PresentableSentimentResults> {
    var detectSentimentResultsGenerator = RDG.list(CommonRDG.detectSentimentResult(), Range.closed(10, 20))
    override fun next(): PresentableSentimentResults {
        return PresentableSentimentResults(LocalDate.now(),
                detectSentimentResultsGenerator.next(),
                RDG.string(20).next())
    }
}