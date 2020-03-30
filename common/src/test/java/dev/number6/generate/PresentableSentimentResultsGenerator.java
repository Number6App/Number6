package dev.number6.generate;


import dev.number6.comprehend.results.PresentableSentimentResults;
import com.amazonaws.services.comprehend.model.DetectSentimentResult;
import uk.org.fyodor.generators.Generator;
import uk.org.fyodor.range.Range;

import java.time.LocalDate;
import java.util.List;

public class PresentableSentimentResultsGenerator implements Generator<PresentableSentimentResults> {

    Generator<List<DetectSentimentResult>> detectSentimentResultsGenerator = CommonRDG.list(CommonRDG.detectSentimentResult(), Range.closed(10, 20));

    @Override
    public PresentableSentimentResults next() {
        return new PresentableSentimentResults(LocalDate.now(),
                detectSentimentResultsGenerator.next(),
                CommonRDG.string(20).next());
    }

}