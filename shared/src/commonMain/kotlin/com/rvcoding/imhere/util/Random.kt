package com.rvcoding.imhere.util

interface UUID {
    val uuid: String
}

expect fun randomUUID(): UUID