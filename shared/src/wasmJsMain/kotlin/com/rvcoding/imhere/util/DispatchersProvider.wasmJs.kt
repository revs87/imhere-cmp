package com.rvcoding.imhere.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

actual fun platformCoroutineDispatcherIO(): CoroutineDispatcher = Dispatchers.Default