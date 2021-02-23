package dev.number6.keyphrases

import dev.number6.comprehend.results.PresentableKeyPhrasesResults
import dev.number6.keyphrases.dagger.ChannelMessagesHandlerModule
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
    private val channelMessages: ChannelMessages = mockk()
    private val results: PresentableKeyPhrasesResults = mockk()
    private val toResults: ChannelMessagesToComprehensionResultsFunction<PresentableKeyPhrasesResults> = mockk()
    private val resultConsumer: ComprehensionResultsConsumer<PresentableKeyPhrasesResults> = mockk(relaxUnitFun = true)

    private var module: ChannelMessagesHandlerModule = ChannelMessagesHandlerModule()

    @Test
    fun providesChannelHandler() {
        val handler = module.providesMessageComprehension(toResults, resultConsumer)
        every { toResults.apply(channelMessages.hint(PresentableKeyPhrasesResults::class, 1)) } returns results
        handler.handle(channelMessages)
        verify { toResults.apply(channelMessages) }
        verify { resultConsumer.accept(results) }
    }
}