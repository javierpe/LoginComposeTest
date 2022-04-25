package com.javier.impl

import com.javier.api.AuthApi
import com.javier.api.models.LoginState
import com.javier.api.models.LoginStatus
import com.javier.impl.ui.viewModels.LoginViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {

    @Mock
    private lateinit var authApi: AuthApi

    private lateinit var loginViewModel: LoginViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        loginViewModel = LoginViewModel(authApi)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `LogIn should be called from LoginViewModel`() = runTest {
        loginViewModel.logIn("", "")
        verify(authApi).hashCode()
    }

    @Test
    fun `loginStatus should have a valid state`() = runTest {
        val state = LoginState.LoginFailed(status = LoginStatus.NONE)
        val flow = flow { emit(state) }
        whenever(authApi.logIn("", "")).thenReturn(flow)

        loginViewModel.logIn("", "")
        delay(200)
        assert(loginViewModel.loginStatus.value == state)
    }
}