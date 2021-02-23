package dev.number6.message

import dev.number6.comprehend.results.ComprehensionResults

class ChannelMessagesComprehensionHandler<T : ComprehensionResults<*, *>>(private val channelMessagesToComprehensionResultsFunction: ChannelMessagesToComprehensionResultsFunction<T>,
                                                                          private val comprehensionResultsConsumer: ComprehensionResultsConsumer<T>) : ChannelMessagesHandler {
    override fun handle(channelMessages: ChannelMessages) {
        val results = channelMessagesToComprehensionResultsFunction.apply(channelMessages)
        comprehensionResultsConsumer.accept(results)
    }
}