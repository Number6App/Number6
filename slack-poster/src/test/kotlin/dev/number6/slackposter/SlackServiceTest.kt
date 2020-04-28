package dev.number6.slackposter

import com.amazonaws.services.lambda.runtime.LambdaLogger
import dev.number6.slackposter.model.ChannelSummaryImageBuilder
import dev.number6.slackposter.port.SlackPosterPort
import io.mockk.Called
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.io.IOException

@ExtendWith(MockKExtension::class)
internal class SlackServiceTest {

    private val logger: LambdaLogger = mockk(relaxUnitFun = true)

    private val slackPosterPort: SlackPosterPort = mockk(relaxUnitFun = true)

    @Test
    @Throws(IOException::class)
    fun postToSlackIfLastUpdate() {
        val testee = SlackService(slackPosterPort)
        val image: ChannelSummaryImage = ChannelSummaryImageBuilder.finalImage().build()
        testee.handleNewImage(image, logger)
        verify { slackPosterPort.postMessageToChannel(any(), any()) }
    }

    @Test
    fun doNotPostToSlackIfNotLastUpdate() {
        val testee = SlackService(slackPosterPort)
        val image: ChannelSummaryImage = ChannelSummaryImageBuilder.notFinalImage().build()
        testee.handleNewImage(image, logger)
        verify { slackPosterPort wasNot Called }
    }
}