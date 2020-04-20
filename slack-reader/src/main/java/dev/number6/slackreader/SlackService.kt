package dev.number6.slackreader

import com.amazonaws.services.lambda.runtime.LambdaLogger
import dev.number6.slackreader.model.WorkspaceMessages
import dev.number6.slackreader.port.SlackPort
import dev.number6.slackreader.port.SlackReaderConfigurationPort
import java.time.LocalDate

class SlackService(private val slackPort: SlackPort, private val config: SlackReaderConfigurationPort) {
    fun getMessagesOnDate(comprehensionDate: LocalDate, logger: LambdaLogger): WorkspaceMessages {
        val blacklistedChannels = config.getBlacklistedChannels()
        return slackPort.getChannelList(logger).stream()
                .filter { c -> !blacklistedChannels.contains(c.name) }
                .reduce(WorkspaceMessages(comprehensionDate),
                        { wm, c ->
                            wm.add(c.name,
                                    slackPort.getMessagesForChannelOnDate(c, comprehensionDate, logger)
                                            .map { m -> m.text })
                        },
                        { _, wm2 -> wm2 })
    }
}