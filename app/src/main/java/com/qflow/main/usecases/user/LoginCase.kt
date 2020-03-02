package com.qflow.main.usecases.user

import android.widget.Button
import android.widget.EditText
import com.qflow.main.core.Failure
import com.qflow.main.repository.UserRepository
import com.qflow.main.usecases.Either
import com.qflow.main.usecases.UseCase
import kotlinx.coroutines.CoroutineScope

/**
 * LoginCase
 * */
class LoginCase (private val userRepository: UserRepository):
    UseCase<Long, LoginCase.Params, CoroutineScope>() {

    override suspend fun run(params: Params): Either<Failure, Long> {
       /* //TODO
        fun validateUser(mail: String,pass:String):Boolean{
            var valid = true;
            if(mail.isEmpty()) {
                print("Error")
                valid=true
            }
            if(pass.isEmpty()) {
                print("Error")
                valid = false
            }
        }*/
        val result = userRepository
            .saveUser(params.selectedPass, params.selectedMail)

       return when(result){
           is Either.Left -> Either.Left(result.a)
           is Either.Right -> Either.Right(result.b)
       }

    }*/

    class Params(val selectedPass: String, val selectedMail: String )


}