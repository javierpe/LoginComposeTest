package com.javier.impl

import com.javier.api.models.LoginState
import com.javier.api.models.LoginStatus
import com.javier.impl.impls.AuthApiImpl
import com.javier.impl.internals.AuthRepositoryApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
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
class AuthApiImplTest {

    @Mock
    private lateinit var authRepository: AuthRepositoryApi

    private lateinit var authApiImpl: AuthApiImpl

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        authApiImpl = AuthApiImpl(authRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Load current user should be called when currentUser is executed`() {
        authApiImpl.currentUser()
        verify(authRepository).currentUser()
    }

    @Test
    fun `LoginStatus EMPTY_PARAMS should be emit when credentials are empty`() = runTest {
        var state: LoginState? = null
        authApiImpl.logIn( "", "").collect() {
            state = it
        }

        val isValid = state != null &&
                state is LoginState.LoginFailed &&
                (state as LoginState.LoginFailed).status == LoginStatus.EMPTY_PARAMS

        assert(isValid)
    }

    @Test
    fun `Log in should be called when name and password are valid params`() = runTest {
        val userName = "a"
        val userPassword = "b"

        val flow = flow { emit(LoginState.LoginFailed(status = LoginStatus.INVALID_CREDENTIALS)) }
        whenever(authRepository.logIn(userName, userPassword)).thenReturn(flow)

        authApiImpl.logIn(userName, userPassword).collect()
        verify(authRepository).logIn(userName, userPassword)
    }
}