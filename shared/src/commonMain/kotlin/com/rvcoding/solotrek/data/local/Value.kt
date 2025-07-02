package com.rvcoding.solotrek.data.local

sealed interface Value {
    data class BooleanVal(val value: Boolean) : Value
    data class IntegerVal(val value: Int) : Value
    data class LongVal(val value: Long) : Value
    data class StringVal(val value: String) : Value
    data class SecureStringVal(val value: String) : Value
    data class MapStringVal(val value: Map<String, String>) : Value
}