package com.qflow.main.usecases.user

import android.content.ContentValues.TAG
import android.provider.ContactsContract
import android.widget.Button
import android.widget.EditText
import com.qflow.main.core.Failure
import com.qflow.main.repository.UserRepository
import com.qflow.main.usecases.Either
import com.qflow.main.usecases.UseCase
import kotlinx.android.synthetic.main.fragment_login.view.*
import kotlinx.coroutines.CoroutineScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import android.util.Log
import android.widget.Toast
import android.text.TextUtils
import com.google.android.gms.tasks.SuccessContinuation


/**
 * LoginCase
 *
 * */
class LoginCase(private val userRepository: UserRepository) :
    UseCase<Long, LoginCase.Params, CoroutineScope>() {

    private lateinit var auth: FirebaseAuth
    private val TAG = "Msg :"

    override suspend fun run(params: Params): Either<Failure, Long> {

        checkUser()
        return when(val res = validate(params.selectedMail, params.selectedPass)){
            is Either.Left -> Either.Left(res.a)
            is Either.Right -> userRepository.saveUser(params.selectedPass, params.selectedMail)
        }
    }


    fun checkUser() {
        var user = FirebaseAuth.getInstance().currentUser
        if (user != null) Log.d(TAG, "You´re SignIn")
        else Log.d(TAG, "You´re not SignIn")

    }

    fun validate(email: String, pass: String): Either<Failure, Unit> {
        var valid = true
        if (email.isEmpty() || pass.isEmpty()) {
            Log.d(TAG, "Required filds")
            return Either.Left(Failure.NullResult())
        }
        return Either.Right(Unit)
    }

    fun signIn(email: String, pass: String) {

        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "You´re signin in succesfully ")
                val user = auth.currentUser
            } else
                Log.w(TAG, "createUserWithEmail:failure", task.exception)
        }
    }

    class Params(val selectedPass: String, val selectedMail: String)


}