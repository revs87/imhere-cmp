package com.rvcoding.solotrek.di

import com.rvcoding.solotrek.CommonContext
import com.rvcoding.solotrek.data.local.ksafe.KSafeWrapper
import eu.anifantakis.lib.ksafe.KSafe
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformSharedModule(): Module = module(createdAtStart = true) {
    single { CommonContext }
    single<KSafe> { KSafe() }
    single<KSafeWrapper> { KSafeWrapper() }
}
