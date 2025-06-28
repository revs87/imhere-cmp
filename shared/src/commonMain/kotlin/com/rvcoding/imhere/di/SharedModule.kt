package com.rvcoding.imhere.di

import com.rvcoding.imhere.data.location.KMPLocation
import com.rvcoding.imhere.data.location.LocationProviderFactory
import org.koin.core.module.Module
import org.koin.dsl.module


expect fun platformSharedModule(): Module

val sharedModule: Module = module {
    single<KMPLocation> { LocationProviderFactory().create(get()) }
}