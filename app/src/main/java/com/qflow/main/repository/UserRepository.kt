package com.qflow.main.repository
import android.content.ContentValues.TAG
import android.util.Log

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.qflow.main.core.BaseRepository
import com.qflow.main.core.Failure
import com.qflow.main.domain.adapters.UserAdapter
import com.qflow.main.domain.local.database.AppDatabase
import com.qflow.main.domain.local.database.user.UserDB
import com.qflow.main.usecases.Either


/**
 * UserRepository, connects with firebase and the database to gets us what we need related
 * to the user
 * */
interface UserRepository {

    fun saveUser(selectedPass: String, email: String): Either<Failure, Long>
    fun signIn(email: String,pass:String)
    fun currentUserauth(): FirebaseUser?
    fun checkUser(): Either<Failure, Any>
    fun signOut()

    class General
    constructor(
        private var auth: FirebaseAuth = FirebaseAuth.getInstance(),
        private val appDatabase: AppDatabase,
        val userAdapter: UserAdapter
    ) : BaseRepository(), UserRepository {

        override fun saveUser(
            selectedPass: String,
            selectedEmail: String
        ): Either<Failure, Long> {
            val user = UserDB(
                password = selectedPass,
                mail = selectedEmail
            )

            appDatabase.userDatabaseDao.insert(user)
            val id = appDatabase.userDatabaseDao.correctUser(user.mail,user.password)?.userId
            return if (id!= null) {
                Either.Right(id)
            } else {
                Either.Left<Failure.NullResult, Any>(Failure.NullResult())
            }
        }
        /**
         * return currentUser from firebaseAuth
         * */
        override fun currentUserauth(): FirebaseUser? {
            return auth.currentUser
        }
        /**
         * use FirebaseAuth method  sigInWithEmailAndPassword,
         * validates email and pass and then sigIn a user
         * @email
         * @pass
         * */
        override fun signIn(email: String,pass: String){
            auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "You´re signin in succesfully ")
                    //val user=currentUserauth()

                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)

                }
            }
        }
        /**
         * return active session user
         * if there not user then return null,
         * in other cases return sussesful
         * */
        override fun checkUser():Either<Failure,Any> {
            val c = currentUserauth()
            //user session still active
            return if (c!= null) {
                Either.Right(c)
            } else {
                Either.Left<Failure.NullResult, Any>(Failure.NullResult())
            }
        }
        /**
         * use firebase method signOut
         * to signOut a user
         * */
        override fun signOut() {
           auth.signOut()
        }
    }
}
