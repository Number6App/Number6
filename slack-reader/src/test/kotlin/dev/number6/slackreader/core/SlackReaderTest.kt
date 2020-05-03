package dev.number6.slackreader.core

import com.amazonaws.services.lambda.runtime.LambdaLogger
import dev.number6.db.adaptor.DynamoBasicDatabaseAdaptor
import dev.number6.slackreader.SlackReader
import dev.number6.slackreader.SlackService
import dev.number6.slackreader.SnsService
import dev.number6.slackreader.generate.SlackReaderRDG
import dev.number6.slackreader.model.WorkspaceMessages
import io.mockk.*
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import uk.org.fyodor.generators.RDG
import uk.org.fyodor.range.Range
import java.time.Clock
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.*

@ExtendWith(MockKExtension::class)
class SlackReaderTest {
    private val logger = mockk<LambdaLogger>()
    private val slackService = mockk<SlackService>()
    private val snsService = mockk<SnsService>(relaxUnitFun = true)
    private val dbService = mockk<DynamoBasicDatabaseAdaptor>(relaxUnitFun = true)
    private val clock = Clock.fixed(Instant.now().minus(RDG.longVal(10L).next(), ChronoUnit.DAYS), ZoneId.systemDefault())
    private var testee: SlackReader? = null

    @BeforeEach
    fun setup() {
        val messages = WorkspaceMessages(LocalDate.now())
        every { slackService.getMessagesOnDate(any(), any()) } returns messages
        testee = SlackReader(slackService, snsService, dbService, clock)
    }

    @Test
    fun useDateInEventIfPresent() {
        val comprehensionDate = LocalDate.of(2018, 12, 25)
        val event = mapOf(SlackReader.COMPREHENSION_DATE_FIELD_NAME to comprehensionDate.toString(),
                "some-other-event" to "bibble")
        testee?.handle(event, logger)
        verify { slackService.getMessagesOnDate(comprehensionDate, logger) }
        confirmVerified(slackService)
    }

    @Test
    fun useYesterdayIfNoDatePresentInEvent() {
        val event = mapOf("some-other-event" to "bibble")
        testee?.handle(event, logger)
        verify { slackService.getMessagesOnDate(LocalDate.now(clock).minusDays(1), logger) }
    }

    @Test
    fun useYesterdayIfNothingPresentInEvent() {
        testee?.handle(HashMap(), logger)
        verify { slackService.getMessagesOnDate(LocalDate.now(clock).minusDays(1), logger) }
    }

    @Test
    fun sendToSnsWhatComesBackFromSlack() {
        val messages = WorkspaceMessages(LocalDate.now(clock).minusDays(1))
        every { slackService.getMessagesOnDate(LocalDate.now(clock).minusDays(1), logger) } returns messages
        testee?.handle(mapOf(), logger)
        verify { snsService.broadcastWorkspaceMessagesForActiveChannels(messages) }
    }

    @Test
    fun saveToDbBeforePostingToSns() {
        val messages = SlackReaderRDG.workspaceMessages(Range.closed(3,10)).next()

        every { slackService.getMessagesOnDate(LocalDate.now(clock).minusDays(1), logger) } returns messages
        testee?.handle(mapOf(), logger)
        verifyOrder {
            dbService.createNewSummaryForChannels(messages.getActiveChannelNames(), any())
            snsService.broadcastWorkspaceMessagesForActiveChannels(messages)
        }
    }
}