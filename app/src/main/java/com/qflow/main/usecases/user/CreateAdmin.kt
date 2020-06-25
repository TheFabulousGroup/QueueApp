package com.qflow.main.usecases.user

import com.qflow.main.core.Failure
import com.qflow.main.domain.adapters.UserAdapter
import com.qflow.main.domain.local.SharedPrefsRepository
import com.qflow.main.repository.UserRepository
import com.qflow.main.usecases.Either
import com.qflow.main.usecases.UseCase
import com.qflow.main.utils.MD5Creator
import com.qflow.main.utils.enums.ValidationFailureType
import kotlinx.coroutines.CoroutineScope

class CreateAdmin(
    private val userRepository: UserRepository,
    private val sharedPrefsRepository: SharedPrefsRepository

) :
    UseCase<String, CreateAdmin.Params, CoroutineScope>() {
    override suspend fun run(params: Params): Either<Failure, String> {
        return when (val result = validPassword(params.selectedPass, params.selectedRepeatPass)) {
            is Either.Left -> Either.Left(result.a)
            is Either.Right -> {
                when (val res= userRepository.createAdmin(
                    params.username,
                    MD5Creator.md5(params.selectedPass),
                    params.selectedEmail,
                    params.selectedNameLastName)){
                    is Either.Left -> Either.Left(res.a)
                    is Either.Right ->{
                        val userDTO = UserAdapter.jsonStringToUserDTO(res.b)
                        sharedPrefsRepository.putUserToken(userDTO.token.toString())
                        sharedPrefsRepository.putUserName(userDTO.username.toString())
                        Either.Right(res.b)
                    }
                }
            }
        }
    }

    private fun validPassword(selectedPass: String, repeatPass: String): Either<Failure, Unit> {
        return if (selectedPass == repeatPass)
            Either.Right(Unit)
        else
            Either.Left(Failure.ValidationFailure(ValidationFailureType.PASSWORDS_NOT_THE_SAME))
    }

    class Params(
        val username: String,
        val selectedEmail: String,
        val selectedPass: String,
        val selectedRepeatPass: String,
        val selectedNameLastName: String
    )

}