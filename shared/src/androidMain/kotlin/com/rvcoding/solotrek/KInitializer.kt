package com.rvcoding.solotrek

import android.content.Context
import androidx.startup.Initializer

internal lateinit var applicationContext: Context
    private set

object KLocationContext

/**
 * https://proandroiddev.com/how-to-avoid-asking-for-android-context-in-kotlin-multiplatform-libraries-api-d280a4adebd2
 *
 * Our personal context injector made with App Startup will:
 * - run on Android only (since androidx-startup a dependency of androidMain)
 * - make applicationContext available only in the Android-specific code module (since it’s in the same module of KLocationInitializer.kt)
 * - be transparent to the users of the library, which will invoke the same API in common code and in all the supported targets.
 * */
class KInitializer : Initializer<KLocationContext> {
    override fun create(context: Context): KLocationContext {
        applicationContext = context.applicationContext
        return KLocationContext
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}