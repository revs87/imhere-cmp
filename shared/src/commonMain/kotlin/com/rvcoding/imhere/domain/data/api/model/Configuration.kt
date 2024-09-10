package com.rvcoding.imhere.domain.data.api.model

import androidx.datastore.preferences.core.stringPreferencesKey


class ConfigurationKeys {
    companion object {
        const val FEATURE_FLAGS_KEY = "featureFlags"
        const val TIMEOUT_KEY = "timeoutInMillis"
        const val SESSION_TTL_KEY = "sessionTtlInMillis"

        const val USER_ID_KEY = "userId"

        fun String.asPreferenceKey() = stringPreferencesKey(this)
    }
}