package com.rvcoding.solotrek.data.local.ksafe

import androidx.compose.runtime.SnapshotMutationPolicy
import androidx.compose.runtime.structuralEqualityPolicy
import kotlin.properties.PropertyDelegateProvider
import kotlin.properties.ReadWriteProperty

inline fun <reified T> KSafeWrapper.mutableStateOf(
    defaultValue: T,
    key: String? = null,
    encrypted: Boolean = true,
    policy: SnapshotMutationPolicy<T> = structuralEqualityPolicy()
): PropertyDelegateProvider<Any?, ReadWriteProperty<Any?, T>> {
    // 'this' is the KSafe instance
    val ksafe = this

    return PropertyDelegateProvider { _, property ->
        val actualKey = key ?: property.name
        val initialValue = ksafe.getDirect<T>(actualKey, defaultValue, encrypted)

        val saver: (newValue: T) -> Unit = { newValueToSave ->
            ksafe.putDirect<T>(actualKey, newValueToSave, encrypted)
        }

        KSafeComposeStateWrapper(
            initialValue = initialValue,
            valueSaver = saver,
            policy = policy
        )
    }
}


expect inline fun <reified T> KSafeWrapper.KSafeComposeStateWrapper(
    initialValue: T,
    noinline valueSaver: (newValue: T) -> Unit,
    policy: SnapshotMutationPolicy<T>
): ReadWriteProperty<Any?, T>
