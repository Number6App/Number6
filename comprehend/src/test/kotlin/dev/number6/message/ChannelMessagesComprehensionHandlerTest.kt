package dev.number6.message

import dev.number6.comprehend.results.PresentableEntityResults
import dev.number6.generate.ComprehendRDG
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class ChannelMessagesComprehensionHandlerTest {

    private val resultsFunction: ChannelMessagesToComprehensionResultsFunction<PresentableEntityResults> = mockk()
    private val resultsConsumer: ComprehensionResultsConsumer<PresentableEntityResults> = mockk(relaxUnitFun = true)

    @Test
    fun convertMessagesAndAppliesConsumer() {
        val channelMessages = ComprehendRDG.channelMessages().next()
        val results = ComprehendRDG.presentableEntityResults().next()
        every { resultsFunction.apply(channelMessages.hint(PresentableEntityResults::class, 1)) } returns results
        val testee = ChannelMessagesComprehensionHandler(resultsFunction, resultsConsumer)
        testee.handle(channelMessages)
        verify(exactly = 1) { resultsFunction.apply(channelMessages) }
        verify(exactly = 1) { resultsConsumer.accept(results) }
    }
}