package com.rvcoding.solotrek

import android.app.Application
import android.content.Context
import androidx.activity.ComponentActivity

actual object CommonContext {
    private lateinit var application: Application
    private lateinit var activity: ComponentActivity

    fun setContext(context: Context) { application = context as Application }
    fun getContext(): Context {
        if (::application.isInitialized.not()) throw NullPointerException("CommonContext Context not initialized")
        return application.applicationContext
    }

    fun setActivity(activity: ComponentActivity) { this.activity = activity }
    fun getActivity(): ComponentActivity {
        if (::activity.isInitialized.not()) throw NullPointerException("CommonContext ComponentActivity not initialized")
        return activity
    }
}