package dev.number6.generate

import com.amazonaws.services.comprehend.model.DetectKeyPhrasesResult
import uk.org.fyodor.generators.Generator
import uk.org.fyodor.generators.RDG

class DetectKeyPhrasesResultGenerator : Generator<DetectKeyPhrasesResult> {
    override fun next(): DetectKeyPhrasesResult {
        return DetectKeyPhrasesResult()
                .withKeyPhrases(RDG.list(ComprehendRDG.keyPhrase()).next())
    }
}
