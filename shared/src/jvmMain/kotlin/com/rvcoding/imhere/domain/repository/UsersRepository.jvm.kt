package com.rvcoding.imhere.domain.repository

import com.rvcoding.imhere.data.repository.UsersRepositoryApiOnlyImpl
import com.rvcoding.imhere.domain.data.api.IHApi

actual class UsersRepositoryPlatformImpl actual constructor(api: IHApi) : UsersRepositoryApiOnlyImpl(api)