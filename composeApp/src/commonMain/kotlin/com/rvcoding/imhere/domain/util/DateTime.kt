package com.rvcoding.imhere.domain.util

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.toLocalDateTime

@OptIn(FormatStringsInDatetimeFormats::class)
val dateTimeFormat = LocalDateTime.Format {
    byUnicodePattern("yyyy-MM-dd HH:mm:ss")
}
fun Long.toLocalDate(): String = dateTimeFormat.format(
    Instant
        .fromEpochMilliseconds(this)
        .toLocalDateTime(TimeZone.currentSystemDefault())
)