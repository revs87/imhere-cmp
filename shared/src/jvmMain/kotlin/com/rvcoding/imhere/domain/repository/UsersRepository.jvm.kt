package com.rvcoding.imhere.domain.repository

import com.rvcoding.imhere.domain.data.api.IHApi
import com.rvcoding.imhere.domain.data.repository.UsersRepositoryApiOnlyImpl

actual class UsersRepositoryPlatformImpl actual constructor(api: IHApi) : UsersRepositoryApiOnlyImpl(api)