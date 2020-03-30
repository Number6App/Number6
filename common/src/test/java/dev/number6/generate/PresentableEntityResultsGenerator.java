package dev.number6.generate;


import dev.number6.comprehend.results.PresentableEntityResults;
import uk.org.fyodor.generators.Generator;

import java.time.LocalDate;
import java.util.Map;

public class PresentableEntityResultsGenerator implements Generator<PresentableEntityResults> {

    Generator<Map<String, Map<String, Long>>> entityResultsGenerator = CommonRDG.map(CommonRDG.string(10),
            CommonRDG.map(CommonRDG.string(10), CommonRDG.longVal(100)));

    @Override
    public PresentableEntityResults next() {
        return new PresentableEntityResults(LocalDate.now(), entityResultsGenerator.next(), CommonRDG.string().next());
    }
}