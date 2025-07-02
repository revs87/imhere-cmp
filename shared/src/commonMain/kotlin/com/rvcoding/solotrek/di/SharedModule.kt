package com.rvcoding.solotrek.di

import com.rvcoding.solotrek.data.permissions.PermissionLauncher
import org.koin.core.module.Module
import org.koin.dsl.module


expect fun platformSharedModule(): Module

val sharedModule: Module = module {
    single { PermissionLauncher }

}