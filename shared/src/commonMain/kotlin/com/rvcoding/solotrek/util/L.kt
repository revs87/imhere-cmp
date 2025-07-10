package com.rvcoding.solotrek.util

import co.touchlab.kermit.Logger
import co.touchlab.kermit.Severity
import co.touchlab.kermit.loggerConfigInit
import co.touchlab.kermit.platformLogWriter
import com.rvcoding.solotrek.PlatformType
import com.rvcoding.solotrek.getPlatformType


object L {
    const val TAG = "SOLO-LOGGER"

    val kLogger = Logger(
        config = loggerConfigInit(
            platformLogWriter(),
            minSeverity = Severity.Debug),
        tag = TAG
    )

    inline fun d(tag: String = TAG, message: () -> String) {
        when (getPlatformType()) {
            is PlatformType.IOS -> {
                /**
                 * TODO:
                 * - Workaround for iOS's "you have to enable iOS KMP debugging in Advanced Settings"
                 * - https://github.com/touchlab/Kermit/issues/380#issuecomment-2730969452
                 * */
                kLogger.i(null, tag, message)
            }
            else -> kLogger.d(null, tag, message)
        }
    }
    inline fun i(tag: String = TAG, message: () -> String) = kLogger.i(null, tag, message)
    inline fun w(tag: String = TAG, message: () -> String) = kLogger.w(null, tag, message)
    inline fun e(tag: String = TAG, message: () -> String) = kLogger.e(null, tag, message)

    inline fun d(throwable: Throwable? = null, tag: String = TAG, message: () -> String) {
        when (getPlatformType()) {
            is PlatformType.IOS -> kLogger.d(throwable, tag, message)
            else -> kLogger.d(throwable, tag, message)
        }
    }
    inline fun i(throwable: Throwable? = null, tag: String = TAG, message: () -> String) = kLogger.i(throwable, tag, message)
    inline fun w(throwable: Throwable? = null, tag: String = TAG, message: () -> String) = kLogger.w(throwable, tag, message)
    inline fun e(throwable: Throwable? = null, tag: String = TAG, message: () -> String) = kLogger.e(throwable, tag, message)
}
