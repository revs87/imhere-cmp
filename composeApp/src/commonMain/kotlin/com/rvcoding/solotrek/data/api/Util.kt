package com.rvcoding.solotrek.data.api

import io.ktor.client.statement.HttpResponse


fun HttpResponse.statusSuccess(): Boolean = this.status.value in 200..299
