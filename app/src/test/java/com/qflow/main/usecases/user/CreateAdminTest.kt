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
class CreateAdminTest {

    @Mock
    private var userRepositoryMock: UserRepository = mock(UserRepository::class.java)

    val createAdmin: CreateAdmin = CreateAdmin(userRepositoryMock)

    private var job = Job()
    private var coroutineScope = CoroutineScope(Dispatchers.Default + job)

    lateinit var params: CreateAdmin.Params

    @Before
    fun setUp() {
        val username = "TestNameAdmin"
        val selectedPass = "TestPassAdmin"
        val selectedRepeatPass = "TestPassAdmin"
        val selectedNameLastName = "TestLastAdmin"
        val selectedEmail = "TestAdmin@mail"

        params = CreateAdmin.Params(
            username,
            selectedPass,
            selectedRepeatPass,
            selectedNameLastName,
            selectedEmail
        )
    }

    @Test
    fun runTest_paramsCorrect_na() {

        val username = "TestNameAdmin"
        val selectedPass = "TestPassAdmin"
        val selectedNameLastName = "TestLastAdmin"
        val selectedEmail = "TestAdmin@mail"

        Mockito.`when`(
            userRepositoryMock.createAdmin(
                username,
                selectedPass,
                selectedEmail,
                selectedNameLastName
            )
        ).thenReturn(Either.Right("IdTestAdmin"))

        createAdmin.execute({ it ->
            it.either({
                assert(false)
            }, { s ->
                assert(s == "IdTestAdmin")
            })
        }, params, coroutineScope)


    }

    @Test
    fun runTest_paramsIncorrect_goesToError() {

        val username = "TestNameAdmin"
        val selectedPass = "TestPassAdmin"
        val selectedRepeatPass = "TestPassAdmin1"
        val selectedNameLastName = "TestLastAdmin"
        val selectedEmail = "TestAdmin@mail"

        val params = CreateAdmin.Params(
            username,
            selectedPass,
            selectedRepeatPass,
            selectedNameLastName,
            selectedEmail
        )
        Mockito.`when`(
            userRepositoryMock.createAdmin(
                username,
                selectedPass,
                selectedEmail,
                selectedNameLastName
            )
        ).thenReturn(Either.Right("IdTestAdmin"))

        createAdmin.execute({
            it.either(
                {failure ->
                    assert(true)
                }, { s ->
                    assert(false)
                })
        }, params, coroutineScope)
    }


}