package com.qflow.main.usecases.user

import com.qflow.main.core.Failure
import com.qflow.main.repository.UserRepository
import com.qflow.main.usecases.Either
import com.qflow.main.usecases.UseCase
import kotlinx.coroutines.CoroutineScope

/**
 * UseCaseCreateUserInDatabase
 * */
class SaveUserInDatabase (private val userRepository: UserRepository):
    UseCase<Long, SaveUserInDatabase.Params, CoroutineScope>() {
    override suspend fun run(params: Params): Either<Failure, Long> {

        val result = userRepository
            .saveUser(params.username, params.selectedPass, params.selectedMail)

       return when(result){
           is Either.Left -> Either.Left(result.a)
           is Either.Right -> Either.Right(result.b)
       }

    }

    class Params(val username: String, val selectedPass: String, val selectedMail: String )


}