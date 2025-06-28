package com.rvcoding.imhere

import android.app.Application
import android.content.Context

actual object CommonContext {
    private lateinit var application: Application

    fun setUp(context: Context) { application = context as Application }
    fun get(): Context {
        if (::application.isInitialized.not()) throw NullPointerException("CommonContext not initialized")
        return application.applicationContext
    }
}