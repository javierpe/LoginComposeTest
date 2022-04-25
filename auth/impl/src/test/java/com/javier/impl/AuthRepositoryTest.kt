package com.javier.impl

import app.cash.turbine.test
import com.javier.api.models.Account
import com.javier.api.models.LoginState
import com.javier.impl.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class AuthRepositoryTest {

    private lateinit var authRepository: AuthRepository

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        authRepository = AuthRepository()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `LoginSuccess should be emitted with valid credentials`() = runTest {
        authRepository.logIn("Javier", "123").test {
            Thread.sleep(DELAY)
            assert(awaitItem() is LoginState.LoginSuccess)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `LoginFailed should be emitted with invalid credentials`() = runTest {
        authRepository.logIn("a", "b").test {
            Thread.sleep(DELAY)
            val failed = awaitItem()
            assert(failed is LoginState.LoginFailed)
            awaitComplete()
        }
    }

    @Test
    fun `Account should be created when login is success`() = runTest {
        authRepository.logIn("Javier", "123").test {
            Thread.sleep(DELAY)
            awaitItem()
            Thread.sleep(DELAY)
            val account = authRepository.currentUser().value
            assert(account == Account("JAVIER"))
            cancelAndIgnoreRemainingEvents()
        }
    }

    companion object {
        const val DELAY: Long = 700
    }
}