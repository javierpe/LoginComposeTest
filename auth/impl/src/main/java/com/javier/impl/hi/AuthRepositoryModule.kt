package com.javier.impl.hi

import com.javier.impl.internals.AuthRepositoryApi
import com.javier.impl.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthRepositoryModule {

    @Binds
    abstract fun bindAuthRepositoryApi(
        impl: AuthRepository
    ): AuthRepositoryApi
}