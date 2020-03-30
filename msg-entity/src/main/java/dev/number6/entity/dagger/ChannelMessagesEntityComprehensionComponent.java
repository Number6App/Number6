package dev.number6.entity.dagger;

import dev.number6.CommonModule;
import dev.number6.message.ChannelMessagesHandler;
import dagger.Component;

@Component(modules = {
        CommonModule.class,
        ChannelMessagesHandlerModule.class,
        ComprehensionResultsModule.class
})
public interface ChannelMessagesEntityComprehensionComponent {

    ChannelMessagesHandler getChannelMessagesHandler();
}
