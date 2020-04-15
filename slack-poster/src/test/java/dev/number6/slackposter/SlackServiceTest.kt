package dev.number6.slackposter

import com.amazonaws.services.lambda.runtime.LambdaLogger
import dev.number6.slackposter.model.ChannelSummaryImageBuilder
import dev.number6.slackposter.port.SlackPort
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.junit.jupiter.MockitoExtension
import java.io.IOException

@ExtendWith(MockitoExtension::class)
@Disabled("do something about Mockito")
internal class SlackServiceTest {
    private val logger: LambdaLogger = mock(LambdaLogger::class.java)

    private val slackPort: SlackPort = mock(SlackPort::class.java)

    @Test
    @Throws(IOException::class)
    fun postToSlackIfLastUpdate() {
        val testee = SlackService(slackPort)
        val image: ChannelSummaryImage = ChannelSummaryImageBuilder.finalImage().build()
        testee.handleNewImage(image, logger)
        Mockito.verify(slackPort, Mockito.times(1)).postMessageToChannel(any(), any())
    }

    @Test
    fun doNotPostToSlackIfNotLastUpdate() {
        val testee = SlackService(slackPort)
        val image: ChannelSummaryImage = ChannelSummaryImageBuilder.Companion.notFinalImage().build()
        testee.handleNewImage(image, logger)
        Mockito.verifyZeroInteractions(slackPort)
    }
}