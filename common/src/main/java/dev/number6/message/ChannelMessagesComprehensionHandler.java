package dev.number6.message;

import dev.number6.comprehend.results.ComprehensionResults;

public class ChannelMessagesComprehensionHandler<T extends ComprehensionResults<?>>
        implements ChannelMessagesHandler {

    private final ChannelMessagesToComprehensionResultsFunction<T> channelMessagesToComprehensionResultsFunction;
    private final ComprehensionResultsConsumer<T> comprehensionResultsConsumer;

    public ChannelMessagesComprehensionHandler(ChannelMessagesToComprehensionResultsFunction<T> channelMessagesToComprehensionResultsFunction,
                                               ComprehensionResultsConsumer<T> comprehensionResultsConsumer) {

        this.channelMessagesToComprehensionResultsFunction = channelMessagesToComprehensionResultsFunction;
        this.comprehensionResultsConsumer = comprehensionResultsConsumer;
    }

    public void handle(ChannelMessages channelMessages) {

        T results = channelMessagesToComprehensionResultsFunction.apply(channelMessages);

        comprehensionResultsConsumer.accept(results);
    }
}
