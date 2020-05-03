package dev.number6.db.port

import java.time.LocalDate

interface BasicDatabasePort {
    fun createNewSummaryForChannels(channelNames: Collection<String>, comprehensionDate: LocalDate)
}