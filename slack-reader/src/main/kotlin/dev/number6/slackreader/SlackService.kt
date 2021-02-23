package dev.number6.slackreader

import com.amazonaws.services.lambda.runtime.LambdaLogger
import dev.number6.slackreader.model.WorkspaceMessages
import dev.number6.slackreader.port.SlackReaderPort
import dev.number6.slackreader.port.SlackReaderConfigurationPort
import java.time.LocalDate

class SlackService(private val slackReaderPort: SlackReaderPort, private val config: SlackReaderConfigurationPort) {
    fun getMessagesOnDate(comprehensionDate: LocalDate, logger: LambdaLogger): WorkspaceMessages {
        val blacklistedChannels = config.getBlacklistedChannels()
        return slackReaderPort.getChannelList(logger)
                .filter { c -> !blacklistedChannels.contains(c.name) }
                .fold(WorkspaceMessages(comprehensionDate),
                        { wm, c ->
                            wm.add(c.name,
                                    slackReaderPort.getMessagesForChannelOnDate(c, comprehensionDate, logger)
                                            .map { m -> m.text })
                        })
    }
}