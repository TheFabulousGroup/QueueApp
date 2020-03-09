package com.qflow.main.usecases.user


import android.content.ContentValues.TAG
import com.qflow.main.core.Failure
import com.qflow.main.repository.UserRepository
import com.qflow.main.usecases.Either
import com.qflow.main.usecases.UseCase
import kotlinx.coroutines.CoroutineScope
import android.util.Log

/**
 * LoginCase
 *
 * */
class LoginCase(private val userRepository: UserRepository) :
    UseCase<Long, LoginCase.Params, CoroutineScope>() {

    override suspend fun run(params: Params): Either<Failure, Long> {
        return when(val res = validate(params.selectedMail, params.selectedPass)){
            is Either.Left<*, *> ->  Either.Left<Failure.NullResult, Any>(Failure.NullResult())
            is Either.Right -> userRepository.saveUser(params.selectedPass, params.selectedMail)
        }
    }
    /**
    * Check email and pass
    * @email
    * @pass
    * */
    private fun validate(email: String, pass: String): Either<Failure,Unit> {
        return when(email.isEmpty() || pass.isEmpty()){
            true -> Either.Left(Failure.)
            false -> TODO()
        }
    }

    class Params(val selectedPass: String, val selectedMail: String)


}