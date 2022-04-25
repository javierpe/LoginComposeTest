package com.javier.wiring

import com.javier.api.AuthApi
import com.javier.impl.impls.AuthApiImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthModule {

    @Binds
    abstract fun bindAuthApi(
        impl: AuthApiImpl
    ): AuthApi
}