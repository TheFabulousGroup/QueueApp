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
    private val TAG = "Msg :"
    override suspend fun run(params: Params): Either<Failure, Long> {

        fun checkUser() {
            var user = FirebaseAuth.getInstance().currentUser
            if(user!=null) Log.d(TAG,"You´re SignIn")
            else Log.d(TAG,"You´re not SignIn")

        }
        fun validate(email:String,pass:String):Boolean{
           var valid = true
            if (email.isEmpty() || pass.isEmpty()){
                Log.d(TAG, "Required filds")
                valid = false
            }
            else {
                if(params.selectedMail!=email || params.selectedPass!=pass){
                    Log.d(TAG, "Your email or your pass must be wrong ")
                    valid = false
                }
            }
            return valid
       }
        fun signIn(email:String,pass:String) {

            auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG,"You´re signin in succesfully ")
                    val user = auth.currentUser
                }
                else
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
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