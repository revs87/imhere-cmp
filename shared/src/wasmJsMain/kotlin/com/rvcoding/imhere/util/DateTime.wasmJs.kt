package com.rvcoding.imhere.util

import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
actual fun currentTimeMillis(): Long = Clock.System.now().toEpochMilliseconds()