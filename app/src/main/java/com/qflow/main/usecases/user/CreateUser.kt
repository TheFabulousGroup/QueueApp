package com.qflow.main.usecases.user

import com.qflow.main.core.Failure
import com.qflow.main.repository.UserRepository
import com.qflow.main.usecases.Either
import com.qflow.main.usecases.UseCase
import com.qflow.main.utils.enums.ValidationFailureType
import kotlinx.coroutines.CoroutineScope

/**
 * UseCaseCreateUserInDatabase
 * */
class CreateUser(private val userRepository: UserRepository) :
    UseCase<String, CreateUser.Params, CoroutineScope>() {
    override suspend fun run(params: Params): Either<Failure, String> {

        return when (val result = validPassword(params.selectedPass, params.selectedRepeatPass)) {
            is Either.Left -> Either.Left(result.a)
            is Either.Right -> {
                userRepository.createUser(
                    params.username, params.selectedPass,
                    params.selectedEmail, params.selectedNameLastName, params.selectedRepeatPass)
            }
        }
    }

    fun validPassword(selectedPass: String, repeatPass: String): Either<Failure, Unit> {
        return if (selectedPass == repeatPass)
            Either.Right(Unit)
        else
            Either.Left(Failure.ValidationFailure(ValidationFailureType.PASSWORDS_NOT_THE_SAME))
    }

    class Params(
        val username: String, val selectedPass: String, val selectedRepeatPass: String,
        val selectedNameLastName: String, val selectedEmail: String
    )


}