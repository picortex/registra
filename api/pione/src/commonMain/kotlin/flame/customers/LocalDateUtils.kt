package flame.customers

import krono.LocalDate
import kotlinx.datetime.LocalDate as KxLocalDate
import kotlinx.datetime.*

fun LocalDate.toPiOneDate(tz: TimeZone): PiOneDate {
    val date = KxLocalDate(year, monthNumber, dayOfMonth)
    return PiOneDate(
        time = date.atTime(12, 0).toInstant(tz).toEpochMilliseconds(),
        formatted = format("{MMM} {DD}, {YYYY}")
    )
}