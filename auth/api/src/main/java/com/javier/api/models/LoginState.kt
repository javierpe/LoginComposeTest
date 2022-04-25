package com.javier.api.models

sealed class LoginState {

    /**
     * Undefined
     */
    object LoginUndefined : LoginState()

    /**
     * When login is started
     */
    object LoginStart : LoginState()

    /**
     * When login failed
     */
    class LoginFailed(
        val status: LoginStatus = LoginStatus.NONE,
        val exception: Exception? = null
    ) : LoginState()

    /**
     * When login is success
     */
    class LoginSuccess(val account: Account): LoginState()
}