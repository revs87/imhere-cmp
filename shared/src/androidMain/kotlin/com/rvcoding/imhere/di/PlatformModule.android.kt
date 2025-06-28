package com.rvcoding.imhere.di

import com.rvcoding.imhere.CommonContext
import com.rvcoding.imhere.CommonContext.setUp
import com.rvcoding.imhere.data.local.ksafe.KSafeWrapper
import eu.anifantakis.lib.ksafe.KSafe
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module


actual fun platformSharedModule(): Module = module(createdAtStart = true) {
    single<CommonContext> { CommonContext.also { setUp(androidContext()) } }
    single<KSafe> { KSafe(CommonContext.get()) }
    single<KSafeWrapper> { KSafeWrapper() }
}