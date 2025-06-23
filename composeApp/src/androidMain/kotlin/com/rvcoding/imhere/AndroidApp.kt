package com.rvcoding.imhere


import android.app.Application
import com.rvcoding.imhere.di.appModule
import com.rvcoding.imhere.di.initKoin
import com.rvcoding.imhere.di.platformAppModule
import org.koin.android.ext.koin.androidContext


class AndroidApp : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin(
            appModulesList = listOf(appModule)
        ) {
            androidContext(this@AndroidApp)
        }
    }
}
