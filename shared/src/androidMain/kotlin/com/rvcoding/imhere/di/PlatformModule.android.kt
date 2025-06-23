package com.rvcoding.imhere.di

import com.rvcoding.imhere.CommonContext
import com.rvcoding.imhere.CommonContext.setUp
import org.koin.core.module.Module
import org.koin.dsl.module


actual fun platformSharedModule(): Module = module {
    single<CommonContext> { CommonContext.also { setUp(get()) } }
}