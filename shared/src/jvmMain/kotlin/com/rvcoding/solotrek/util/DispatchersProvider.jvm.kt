package com.rvcoding.solotrek.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

actual fun platformCoroutineDispatcherIO(): CoroutineDispatcher = Dispatchers.IO