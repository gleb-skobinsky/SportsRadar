package org.sportsradar.sportsradarapp.common.utils

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.format.char

val SportsRadarAppDateFormat = LocalDateTime.Format {
    day(); char('.'); monthNumber(); char('.'); year()
    char(' ')
    time(LocalTime.Formats.ISO)
}

val TransactionDateFormat = LocalDateTime.Format {
    day(); char('.'); monthNumber(); char('.'); year()
}