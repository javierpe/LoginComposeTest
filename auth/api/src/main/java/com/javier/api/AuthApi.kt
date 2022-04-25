package com.javier.api

import com.javier.api.models.Account
import com.javier.api.models.LoginState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface AuthApi {

    fun currentUser(): StateFlow<Account?>

    suspend fun logIn(userName: String, userPassword: String): Flow<LoginState>
}