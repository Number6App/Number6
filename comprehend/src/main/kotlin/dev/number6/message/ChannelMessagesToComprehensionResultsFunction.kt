package dev.number6.message

import dev.number6.comprehend.results.ComprehensionResults
import java.util.function.Function

interface ChannelMessagesToComprehensionResultsFunction<T : ComprehensionResults<*, *>> : Function<ChannelMessages, T>