package com.rvcoding.solotrek.domain.util

import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlin.test.Test


class CryptoKtTest {

    @Test
    fun `Verify userId is generated correctly`() {
        val userId = "test"
        val expected = "9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08"
        val actual = userId.sha256()
        assertThat(actual).isEqualTo(expected)
    }
}