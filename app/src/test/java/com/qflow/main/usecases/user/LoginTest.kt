package com.qflow.main.usecases.user

import com.qflow.main.repository.UserRepository
import com.qflow.main.usecases.Either
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.runners.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoginTest {

    @Mock
    private var userRepositoryMock: UserRepository = mock(UserRepository::class.java)

    private val loginUser: LoginCase = LoginCase(userRepositoryMock)

    private var job = Job()
    private var coroutineScope = CoroutineScope(Dispatchers.Default + job)

    lateinit var params: LoginCase.Params

    @Before
    fun setUp() {
        val selectedEmail = "Test@mail"
        val selectedPass = "TestPass"
        //En params 1º la pass y luego el email
        params = LoginCase.Params(
            selectedPass,
            selectedEmail
        )
    }
    @Test
    suspend fun runTest_paramsCorrect_na() {
        val selectedPass = "TestPass"
        val selectedEmail = "Test@mail"
        Mockito.`when`(
            userRepositoryMock.signIn(
                selectedEmail,
                selectedPass
            )
        ).thenReturn(Either.Right("LoginTestCorrect"))

        loginUser.execute({ it ->
            it.either({
                assert(false)
            }, { s ->
                assert(s == "LoginTestCorrect")
            })
        }, params, coroutineScope)
    }
    @Test
    suspend fun runTest_paramsIncorrect_goesToError() {

        val selectedPass = "TestPass"
        val selectedEmail = "Test@mail"

        val params = LoginCase.Params(
            selectedPass,
            selectedEmail
        )
        Mockito.`when`(
            userRepositoryMock.signIn(
                selectedEmail,
                selectedPass
            )
        ).thenReturn(Either.Right("LoginTestCorrect"))

        loginUser.execute({
            it.either(
                {failure ->
                    assert(true)
                }, { s ->
                    assert(false)
                })
        }, params, coroutineScope)
    }
}