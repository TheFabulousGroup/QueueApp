package com.qflow.main.usecases.user

import com.qflow.main.domain.local.SharedPrefsRepository
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
class CreateUserTest {

    @Mock
    private var userRepositoryMock: UserRepository = mock(UserRepository::class.java)

    private var prefsRepository: SharedPrefsRepository =mock(SharedPrefsRepository::class.java)
    val createUser: CreateUser = CreateUser(userRepositoryMock, prefsRepository)

    private var job = Job()
    private var coroutineScope = CoroutineScope(Dispatchers.Default + job)

    lateinit var params: CreateUser.Params

    @Before
    fun setUp() {
        val username = "TestName"
        val selectedPass = "TestPass"
        val selectedRepeatPass = "TestPass"
        val selectedNameLastName = "TestLast"
        val selectedEmail = "Test@mail"

        params = CreateUser.Params(
            username,
            selectedPass,
            selectedRepeatPass,
            selectedNameLastName,
            selectedEmail
        )
    }

    @Test
    suspend fun runTest_paramsCorrect_na() {

        val username = "TestName"
        val selectedPass = "TestPass"
        val selectedNameLastName = "TestLast"
        val selectedEmail = "Test@mail"

        Mockito.`when`(
            userRepositoryMock.createUser(
                username,
                selectedPass,
                selectedEmail,
                selectedNameLastName
            )
        ).thenReturn(Either.Right("IdTest"))

        createUser.execute({ it ->
            it.either({
                assert(false)
            }, { s ->
                assert(s == "IdTest")
            })
        }, params, coroutineScope)


    }

    @Test
    suspend fun runTest_paramsIncorrect_goesToError() {

        val username = "TestName"
        val selectedPass = "TestPass"
        val selectedRepeatPass = "TestPass1"
        val selectedNameLastName = "TestLast"
        val selectedEmail = "Test@mail"

        val params = CreateUser.Params(
            username,
            selectedPass,
            selectedRepeatPass,
            selectedNameLastName,
            selectedEmail
        )
        Mockito.`when`(
            userRepositoryMock.createUser(
                username,
                selectedPass,
                selectedEmail,
                selectedNameLastName
            )
        ).thenReturn(Either.Right("IdTest"))

        createUser.execute({
            it.either(
                {failure ->
                assert(true)
            }, { s ->
                assert(false)
            })
        }, params, coroutineScope)
    }


}