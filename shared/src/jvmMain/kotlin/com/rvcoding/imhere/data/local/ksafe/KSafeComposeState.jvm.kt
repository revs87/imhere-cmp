package com.rvcoding.imhere.data.local.ksafe

import androidx.compose.runtime.SnapshotMutationPolicy
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

actual inline fun <reified T> KSafeWrapper.KSafeComposeStateWrapper(
    initialValue: T,
    noinline valueSaver: (T) -> Unit,
    policy: SnapshotMutationPolicy<T>
): ReadWriteProperty<Any?, T> {
    val ksafeInstance = this

    return object : ReadWriteProperty<Any?, T> {
        override fun getValue(thisRef: Any?, property: KProperty<*>): T {
            return ksafeInstance.getDirect<T>(key = property.name, initialValue, false)
        }

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
            ksafeInstance.putDirect<T>(key = property.name, value, false)
        }
    }
}