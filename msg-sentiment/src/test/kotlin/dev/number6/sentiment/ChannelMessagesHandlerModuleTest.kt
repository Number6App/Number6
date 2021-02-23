package dev.number6.sentiment

import dev.number6.comprehend.results.PresentableSentimentResults
import dev.number6.message.ChannelMessages
import dev.number6.message.ChannelMessagesToComprehensionResultsFunction
import dev.number6.message.ComprehensionResultsConsumer
import dev.number6.sentiment.dagger.ChannelMessagesHandlerModule
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class ChannelMessagesHandlerModuleTest {
    private var channelMessages: ChannelMessages = mockk()
    private var results: PresentableSentimentResults = mockk()
    private var toResults: ChannelMessagesToComprehensionResultsFunction<PresentableSentimentResults> = mockk()
    private var resultConsumer: ComprehensionResultsConsumer<PresentableSentimentResults> = mockk(relaxUnitFun = true)

    private var module: ChannelMessagesHandlerModule = ChannelMessagesHandlerModule()

    @Test
    fun providesChannelHandler() {
        val handler = module.providesMessageComprehension(toResults, resultConsumer)
        every { toResults.apply(channelMessages.hint(PresentableSentimentResults::class, 1)) } returns results
        handler.handle(channelMessages)
        verify { toResults.apply(channelMessages) }
        verify { resultConsumer.accept(results) }
    }
}