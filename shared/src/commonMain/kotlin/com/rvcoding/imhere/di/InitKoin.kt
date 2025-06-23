package com.rvcoding.imhere.di

import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration


fun initKoin(
    appModulesList: List<Module> = emptyList(),
    config: KoinAppDeclaration? = null
) = startKoin {
    config?.invoke(this)
    modules(
        sharedModule,
        //platformSharedModule(), //TODO crashes
        *appModulesList.toTypedArray()
    )
}
