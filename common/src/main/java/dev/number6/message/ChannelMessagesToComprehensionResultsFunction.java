package dev.number6.message;

import dev.number6.comprehend.results.ComprehensionResults;

import java.util.function.Function;

public interface ChannelMessagesToComprehensionResultsFunction<T extends ComprehensionResults<?>> extends Function<ChannelMessages, T> {


}
