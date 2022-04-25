package com.javier.wiring

import com.javier.api.AuthLoaderApi
import com.javier.impl.impls.AuthLoaderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthLoaderModule {

    @Binds
    abstract fun bindAuthLoader(
        impl: AuthLoaderImpl
    ): AuthLoaderApi
}