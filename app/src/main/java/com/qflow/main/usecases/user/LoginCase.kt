package com.qflow.main.usecases.user


import com.qflow.main.core.Failure
import com.qflow.main.repository.UserRepository
import com.qflow.main.usecases.Either
import com.qflow.main.usecases.UseCase
import com.qflow.main.utils.enums.ValidationFailureType
import kotlinx.coroutines.CoroutineScope

/**
 * LoginCase
 * */
class LoginCase(private val userRepository: UserRepository) :
    UseCase<String, LoginCase.Params, CoroutineScope>() {

    override suspend fun run(params: Params): Either<Failure, String> {
        return when (val res = validate(params.selectedMail, params.selectedPass)) {
            is Either.Left -> res
            is Either.Right -> userRepository.signIn(params.selectedMail, params.selectedPass)
        }
    }

    private fun validate(email: String, pass: String): Either<Failure, Unit> {
        return when (email.isEmpty() || pass.isEmpty()) {
            true -> Either.Left(Failure.ValidationFailure(ValidationFailureType.EMAIL_OR_PASSWORD_EMPTY))
            false -> Either.Right(Unit)
        }
    }

    class Params(val selectedPass: String, val selectedMail: String)


}