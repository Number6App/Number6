package dev.number6.generate;


import dev.number6.comprehend.results.PresentableKeyPhrasesResults;
import uk.org.fyodor.generators.Generator;

import java.time.LocalDate;
import java.util.Map;

public class PresentableKeyPhrasesResultsGenerator implements Generator<PresentableKeyPhrasesResults> {

    Generator<Map<String, Long>> keyPhrasesResultsGenerator = CommonRDG.map(CommonRDG.string(10),
            CommonRDG.longVal(100));

    @Override
    public PresentableKeyPhrasesResults next() {
        return new PresentableKeyPhrasesResults(LocalDate.now(), keyPhrasesResultsGenerator.next(), CommonRDG.string().next());
    }
}