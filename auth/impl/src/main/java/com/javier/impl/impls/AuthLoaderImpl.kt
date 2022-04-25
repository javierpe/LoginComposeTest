package com.javier.impl.impls

import androidx.compose.runtime.Composable
import com.javier.api.AuthLoaderApi
import com.javier.impl.ui.screens.LoginScreen
import javax.inject.Inject

class AuthLoaderImpl @Inject constructor(): AuthLoaderApi {

    @Composable
    override fun Load() {
        LoginScreen()
    }
}