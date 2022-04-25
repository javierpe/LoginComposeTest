package com.javier.impl.impls

import com.javier.api.AuthApi
import com.javier.api.models.Account
import com.javier.api.models.LoginState
import com.javier.api.models.LoginStatus
import com.javier.impl.internals.AuthRepositoryApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Use cases
 */
class AuthApiImpl @Inject constructor(
    private val authRepository: AuthRepositoryApi
): AuthApi {

    override fun currentUser(): StateFlow<Account?> {
        return authRepository.currentUser()
    }

    override suspend fun logIn(
        userName: String,
        userPassword: String
    ): Flow<LoginState> = flow{
        if (userName.isNotBlank() && userPassword.isNotBlank()) {
            emit(LoginState.LoginStart)
            authRepository.logIn(userName, userPassword).collect() { emit(it) }
        } else {
            emit(LoginState.LoginFailed(status = LoginStatus.EMPTY_PARAMS))
        }
    }
}