package com.rvcoding.imhere.data.local.ksafe

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

inline operator fun <reified T> KSafeWrapper.invoke(
    defaultValue: T,
    key: String? = null,
    encrypted: Boolean = true
): ReadWriteProperty<Any?, T> {
    val ksafeInstance = this

    return object : ReadWriteProperty<Any?, T> {
        override fun getValue(thisRef: Any?, property: KProperty<*>): T {
            return ksafeInstance.getDirect<T>(key = key ?: property.name, defaultValue, encrypted)
        }

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
            ksafeInstance.putDirect<T>(key = key ?: property.name, value, encrypted)
        }
    }
}