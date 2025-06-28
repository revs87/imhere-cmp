package com.rvcoding.imhere.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface DispatchersProvider {
    val main: CoroutineDispatcher
        get() = Dispatchers.Main
    val mainImmediate: CoroutineDispatcher
        get() = Dispatchers.Main.immediate
    val default: CoroutineDispatcher
        get() = Dispatchers.Default
    val io: CoroutineDispatcher
        /**
         * ios -> internal DefaultIoScheduler
         * wasmJs -> Dispatchers.Default
         * */
        get() = platformCoroutineDispatcherIO()
}

expect fun platformCoroutineDispatcherIO(): CoroutineDispatcher