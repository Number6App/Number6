package dev.number6.entity

import dev.number6.comprehend.results.PresentableEntityResults
import dev.number6.entity.dagger.ChannelMessagesHandlerModule
import dev.number6.message.ChannelMessages
import dev.number6.message.ChannelMessagesToComprehensionResultsFunction
import dev.number6.message.ComprehensionResultsConsumer
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class ChannelMessagesHandlerModuleTest {
    private var channelMessages: ChannelMessages = mockk()
    private var results: PresentableEntityResults = mockk()
    private var toResults: ChannelMessagesToComprehensionResultsFunction<PresentableEntityResults> = mockk()
    private var resultConsumer: ComprehensionResultsConsumer<PresentableEntityResults> = mockk(relaxUnitFun = true)

    private var module = ChannelMessagesHandlerModule()

    @Test
    fun providesChannelHandler() {
        val handler = module.providesMessageComprehension(toResults, resultConsumer)
        every { toResults.apply(channelMessages.hint(PresentableEntityResults::class, 1)) } returns results
        handler.handle(channelMessages)
        verify(exactly = 1) { toResults.apply(channelMessages) }
        verify(exactly = 1) { resultConsumer.accept(results) }
    }
}