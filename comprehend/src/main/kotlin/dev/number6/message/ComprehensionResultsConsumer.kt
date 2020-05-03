package dev.number6.message

import dev.number6.comprehend.results.ComprehensionResults
import java.util.function.Consumer

interface ComprehensionResultsConsumer<T : ComprehensionResults<*>> : Consumer<T>