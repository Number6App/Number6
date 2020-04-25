package dev.number6.message

import dev.number6.comprehend.results.PresentableEntityResults
import dev.number6.generate.CommonRDG
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
@Disabled("replace Mockito")
internal class ChannelMessagesComprehensionHandlerTest {
    @Mock
    var resultsFunction: ChannelMessagesToComprehensionResultsFunction<PresentableEntityResults?>? = null

    @Mock
    var resultsConsumer: ComprehensionResultsConsumer<PresentableEntityResults?>? = null

    @Test
    fun convertMessagesAndAppliesConsumer() {
        val channelMessages = CommonRDG.channelMessages().next()
        val results = CommonRDG.presentableEntityResults().next()
        Mockito.`when`(resultsFunction!!.apply(ArgumentMatchers.any())).thenReturn(results)
//        val testee = ChannelMessagesComprehensionHandler(resultsFunction, resultsConsumer)
//        testee.handle(channelMessages)
//        Mockito.verify(resultsFunction).apply(channelMessages)
//        Mockito.verify(resultsConsumer).accept(results)
    }
}