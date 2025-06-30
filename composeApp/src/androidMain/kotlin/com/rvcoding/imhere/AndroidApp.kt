package com.rvcoding.imhere


import android.app.Application
import com.rvcoding.imhere.di.appModule
import com.rvcoding.imhere.di.initKoin
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
