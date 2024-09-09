package com.rvcoding.imhere.data.local

import com.rvcoding.imhere.domain.data.local.dataStoreFileName


actual fun getDataStoreFilePath(): String = "/$dataStoreFileName"