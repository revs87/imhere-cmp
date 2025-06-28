package com.rvcoding.imhere.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

actual fun platformCoroutineDispatcherIO(): CoroutineDispatcher = Dispatchers.IO