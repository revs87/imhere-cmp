package com.rvcoding.solotrek.domain.repository

import com.rvcoding.solotrek.data.repository.UsersRepositoryApiOnlyImpl
import com.rvcoding.solotrek.domain.data.api.SoloTrekApi

actual class UsersRepositoryPlatformImpl actual constructor(api: SoloTrekApi) : UsersRepositoryApiOnlyImpl(api)