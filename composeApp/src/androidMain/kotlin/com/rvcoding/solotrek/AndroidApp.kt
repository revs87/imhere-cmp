package com.rvcoding.solotrek


import android.app.Application
import com.rvcoding.solotrek.di.appModule
import com.rvcoding.solotrek.di.initKoin
import org.koin.android.ext.koin.androidContext


class AndroidApp : Application() {
    override fun onCreate() {
        super.onCreate()

        CommonContext.setContext(this)

        initKoin(
            appModules = listOf(appModule)
        ) {
            androidContext(this@AndroidApp)
//            androidLogger(level = org.koin.core.logger.Level.ERROR)
        }
    }
}
