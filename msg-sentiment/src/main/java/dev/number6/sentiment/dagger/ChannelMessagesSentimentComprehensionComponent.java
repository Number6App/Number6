package dev.number6.sentiment.dagger;


import dev.number6.CommonModule;
import dev.number6.message.ChannelMessagesHandler;
import dagger.Component;

@Component(modules = {
        CommonModule.class,
        ChannelMessagesHandlerModule.class,
        ComprehensionResultsModule.class
})
public interface ChannelMessagesSentimentComprehensionComponent {

    ChannelMessagesHandler getChannelMessagesHandler();
}
