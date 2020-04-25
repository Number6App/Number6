package dev.number6.comprehend.results

import java.time.LocalDate

open class ComprehensionResults<T>(val comprehensionDate: LocalDate, val results: T, val channelName: String)