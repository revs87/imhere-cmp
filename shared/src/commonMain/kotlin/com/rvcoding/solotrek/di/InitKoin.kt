package com.rvcoding.solotrek.di

import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration


fun initKoin(
    appModules: List<Module> = emptyList(),
    config: KoinAppDeclaration? = null
) = startKoin {
    config?.invoke(this)
    modules(
        platformSharedModule(),
        sharedModule,
        *appModules.toTypedArray()
    )
}
