package com.javier.impl.repository

import com.javier.api.models.Account
import com.javier.api.models.LoginState
import com.javier.api.models.LoginStatus
import com.javier.impl.internals.AuthRepositoryApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.lang.Thread.sleep
import javax.inject.Inject

/**
 * Repository
 */
class AuthRepository @Inject constructor(
    /**
     * Apis: Network, preferences, database, etc.
     */
) : AuthRepositoryApi {

    private val _account = MutableStateFlow<Account?>(null)

    override fun currentUser(): StateFlow<Account?> {
        /**
         * Load from local and set _account value
         */
        return _account
    }

    override suspend fun logIn(
        userName: String,
        userPassword: String
    ): Flow<LoginState> = channelFlow{
        withContext(Dispatchers.IO) {
            try {
                if (userName.lowercase() == VALID_USER_NAME.lowercase() &&
                    userPassword == VALID_USER_PASSWORD
                ) {
                    val account = Account(VALID_USER_NAME.uppercase())
                    delay(LOGIN_DELAY)
                    send(LoginState.LoginSuccess(account))
                    delay(LOGIN_DELAY)

                    _account.value = account
                } else {
                    send(LoginState.LoginFailed(status = LoginStatus.INVALID_CREDENTIALS))
                }
            } catch (e: Exception) {
                send(
                    LoginState.LoginFailed(
                        status = LoginStatus.EXCEPTION,
                        exception = e
                    )
                )
            }
        }
    }

    companion object {
        const val VALID_USER_NAME = "Javier"
        const val VALID_USER_PASSWORD = "123"
        const val DELAY : Long = 500
        const val LOGIN_DELAY : Long = 500
    }
}