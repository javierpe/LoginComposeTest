package com.javier.impl.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.javier.api.AuthApi
import com.javier.api.models.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authApi: AuthApi
): ViewModel() {

    private val _loginStatus = MutableStateFlow<LoginState>(LoginState.LoginUndefined)
    val loginStatus: StateFlow<LoginState> = _loginStatus

    fun logIn(
        name: String,
        password: String
    ) {
        viewModelScope.launch {
            authApi.logIn(name, password).collect() {
                _loginStatus.value = it
            }
        }
    }
}