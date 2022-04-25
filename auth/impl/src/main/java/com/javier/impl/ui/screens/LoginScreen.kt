package com.javier.impl.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.javier.api.models.LoginState
import com.javier.api.models.LoginStatus
import com.javier.impl.R
import com.javier.impl.ui.contents.LoginContent
import com.javier.impl.ui.viewModels.LoginViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel()
) {

    val state = viewModel.loginStatus.collectAsState()

    val loading = remember {
        mutableStateOf(false)
    }

    Box {

        when (state.value) {

            is LoginState.LoginStart -> {
                loading.value = true
            }

            is LoginState.LoginFailed -> {
                loading.value = false
                when ((state.value as LoginState.LoginFailed).status) {
                    LoginStatus.INVALID_CREDENTIALS -> {

                        Toast.makeText(
                            LocalContext.current,
                            stringResource(R.string.login_invalid_credentials),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    LoginStatus.EMPTY_PARAMS -> {

                        Toast.makeText(
                            LocalContext.current,
                            stringResource(R.string.login_empty_fields),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    LoginStatus.EXCEPTION -> {

                        Toast.makeText(
                            LocalContext.current,
                            stringResource(R.string.login_exception_label),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    else -> { }
                }
            }

            is LoginState.LoginSuccess -> {
                Toast.makeText(
                    LocalContext.current,
                    stringResource(R.string.login_start_label),
                    Toast.LENGTH_SHORT
                ).show()
            }

            is LoginState.LoginUndefined -> { /* Showcase app */ }
        }

        LoginContent(loading) { name, password ->
            viewModel.logIn(name, password)
        }
    }
}