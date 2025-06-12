package com.rvcoding.imhere.data.local

import com.rvcoding.imhere.applicationContext
import com.rvcoding.imhere.domain.data.local.dataStoreFileName


internal fun getDataStoreFilePath(): String = applicationContext.filesDir.resolve(dataStoreFileName).absolutePath