package com.rvcoding.imhere.domain.data.api.model

import androidx.datastore.preferences.core.stringPreferencesKey


class ConfigurationKeys {
    companion object {
        val FEATURE_FLAGS_KEY = stringPreferencesKey("featureFlags")
        val TIMEOUT_KEY = stringPreferencesKey("timeoutInMillis")
        val SESSION_TTL_KEY = stringPreferencesKey("sessionTtlInMillis")

        val USER_ID_KEY = stringPreferencesKey("userId")
    }
}