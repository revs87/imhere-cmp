package com.rvcoding.imhere.di

import com.rvcoding.imhere.CommonContext
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformAppModule(): Module = module {
    single { CommonContext }
}