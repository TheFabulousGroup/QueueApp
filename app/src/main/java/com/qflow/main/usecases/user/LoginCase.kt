package com.qflow.main.usecases.user


import com.qflow.main.core.Failure
import com.qflow.main.domain.local.SharedPrefsRepository
import com.qflow.main.repository.UserRepository
import com.qflow.main.usecases.Either
import com.qflow.main.usecases.UseCase
import com.qflow.main.utils.enums.ValidationFailureType
import kotlinx.coroutines.CoroutineScope

/**
 * LoginCase
 * */
class LoginCase(
    private val userRepository: UserRepository,
    private val sharedPrefsRepository: SharedPrefsRepository
) :
    UseCase<String, LoginCase.Params, CoroutineScope>() {

    override suspend fun run(params: Params): Either<Failure, String> {
        return when (val res = validate(params.selectedMail, params.selectedPass)) {
            is Either.Left -> res
            is Either.Right ->
                when (val res = userRepository.signIn(params.isAdmin,params.selectedMail, params.selectedPass)) {
                    is Either.Left -> Either.Left(res.a)
                    is Either.Right -> {
                        sharedPrefsRepository.putUserToken(res.b)
                        Either.Right(res.b)
                    }
                }
        }
    }

    private fun validate(email: String, pass: String): Either<Failure, Unit> {
        return when (email.isEmpty() || pass.isEmpty()) {
            true -> Either.Left(Failure.ValidationFailure(ValidationFailureType.EMAIL_OR_PASSWORD_EMPTY))
            false -> Either.Right(Unit)
        }
    }

    class Params(val isAdmin: Boolean, val selectedPass: String, val selectedMail: String)


}