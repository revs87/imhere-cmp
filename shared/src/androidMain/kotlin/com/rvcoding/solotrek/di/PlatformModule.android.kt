package com.rvcoding.solotrek.di

import com.rvcoding.solotrek.CommonContext
import com.rvcoding.solotrek.CommonContext.setContext
import com.rvcoding.solotrek.data.local.ksafe.KSafeWrapper
import eu.anifantakis.lib.ksafe.KSafe
import org.koin.core.module.Module
import org.koin.dsl.module


actual fun platformSharedModule(): Module = module(createdAtStart = true) {
    single<CommonContext> { CommonContext.also { setContext(get()) } }
    single<KSafe> { KSafe(CommonContext.getContext()) }
    single<KSafeWrapper> { KSafeWrapper() }
}