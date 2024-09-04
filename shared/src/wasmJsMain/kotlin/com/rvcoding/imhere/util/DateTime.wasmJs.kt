package com.rvcoding.imhere.util

import kotlinx.datetime.Clock

actual fun currentTimeMillis(): Long = Clock.System.now().toEpochMilliseconds()