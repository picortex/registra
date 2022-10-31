package flame.customers

import kotlinx.datetime.*
import krono.LocalDate

fun PiOneDate.toLocalDate(tz: TimeZone): LocalDate {
    val date = toLocalDateTime(tz).date
    return LocalDate(date.year, date.monthNumber, date.dayOfMonth)
}

fun PiOneDate.toLocalDateTime(tz: TimeZone): LocalDateTime {
    val instant = Instant.fromEpochMilliseconds(time)
    return instant.toLocalDateTime(tz)
}