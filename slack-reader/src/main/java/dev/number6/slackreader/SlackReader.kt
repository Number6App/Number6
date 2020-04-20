package dev.number6.slackreader

import com.amazonaws.services.lambda.runtime.LambdaLogger
import dev.number6.db.port.DatabasePort
import java.time.Clock
import java.time.LocalDate
import javax.inject.Inject

class SlackReader @Inject constructor(private val slackService: SlackService,
                                      private val snsService: SnsService,
                                      private val dbService: DatabasePort,
                                      private val clock: Clock) {
    fun handle(event: Map<String, Any>, logger: LambdaLogger) {
        val dateEvent = event[COMPREHENSION_DATE_FIELD_NAME]
        val summaryDate = if (dateEvent == null || "" == dateEvent) LocalDate.now(clock).minusDays(1) else LocalDate.parse(dateEvent.toString())
        val messages = slackService.getMessagesOnDate(summaryDate, logger)
        dbService.createNewSummaryForChannels(messages.getActiveChannelNames(), messages.comprehensionDate)
        snsService.broadcastWorkspaceMessagesForActiveChannels(messages)
    }

    companion object {
        const val COMPREHENSION_DATE_FIELD_NAME = "comprehensionDate"
    }

}