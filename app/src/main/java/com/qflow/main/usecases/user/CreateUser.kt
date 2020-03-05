package com.qflow.main.usecases.user

import com.qflow.main.core.Failure
import com.qflow.main.repository.UserRepository
import com.qflow.main.usecases.Either
import com.qflow.main.usecases.UseCase
import kotlinx.coroutines.CoroutineScope

/**
 * UseCaseCreateUserInDatabase
 * */
class CreateUser (private val userRepository: UserRepository):
    UseCase<Long, CreateUser.Params, CoroutineScope>() {
    override suspend fun run(params: Params): Either<Failure, Long> {

        val result = userRepository.createUser(params.username, params.selectedPass,
            params.selectedEmail, params.selectedNameLastName, params.selectedRepeatPass)

       return when(result){
           is Either.Left -> Either.Left(result.a)
           is Either.Right -> Either.Right(result.b)
       }

    }

    class Params(val username: String, val selectedPass: String, val selectedRepeatPass: String,
                 val selectedNameLastName: String,  val selectedEmail: String )


}