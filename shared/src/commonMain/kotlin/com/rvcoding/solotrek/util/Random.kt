package com.rvcoding.solotrek.util

interface UUID {
    val uuid: String
}

expect fun randomUUID(): UUID