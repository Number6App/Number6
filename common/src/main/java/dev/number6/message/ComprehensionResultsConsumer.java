package dev.number6.message;

import dev.number6.comprehend.results.ComprehensionResults;

import java.util.function.Consumer;

public interface ComprehensionResultsConsumer<T extends ComprehensionResults<?>> extends Consumer<T> {
}
