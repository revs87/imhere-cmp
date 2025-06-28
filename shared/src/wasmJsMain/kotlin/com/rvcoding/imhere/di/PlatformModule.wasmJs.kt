package com.rvcoding.imhere.di

import com.rvcoding.imhere.CommonContext
import com.rvcoding.imhere.data.local.ksafe.KSafeWrapper
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformSharedModule(): Module = module(createdAtStart = true) {
    single { CommonContext }
    single<KSafeWrapper> { KSafeWrapper() }
}
