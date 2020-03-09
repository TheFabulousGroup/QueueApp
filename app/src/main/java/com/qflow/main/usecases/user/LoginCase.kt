package com.qflow.main.usecases.user

import android.content.ContentValues.TAG
import android.provider.ContactsContract
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import com.qflow.main.core.Failure
import com.qflow.main.repository.UserRepository
import com.qflow.main.usecases.Either
import com.qflow.main.usecases.UseCase
import kotlinx.android.synthetic.main.login_fragment.view.*
import kotlinx.coroutines.CoroutineScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import android.util.Log
import android.widget.Toast


/**
 * LoginCase
 *
 * */
class LoginCase (private val userRepository: UserRepository):
    UseCase<Long, LoginCase.Params, CoroutineScope>() {
    private lateinit var auth: FirebaseAuth
    private fun sigIn(email: String, pass:String){
        auth = FirebaseAuth.getInstance()
    }
    override suspend fun run(params: Params): Either<Failure, Long> {

        fun validate(email:String,pass:String):Boolean{
           var valid = true
            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)){
                print("Required filds")
                valid = false
            }
            else valid=valid &&(email==null || pass==null)
            return valid
       }
        fun signIn(email:String,pass:String) {
            val user = auth.currentUser
            auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG,"SigIn")
                } else {

                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    //Toast.makeText(this, "Authentication failed.",Toast.LENGTH_SHORT).show()
                }

            }
        }
        val result = userRepository
            .saveUser(params.selectedPass, params.selectedMail)

       return when(result){
           is Either.Left -> Either.Left(result.a)
           is Either.Right -> Either.Right(result.b)
       }

    }

    class Params(val selectedPass: String, val selectedMail: String )


}