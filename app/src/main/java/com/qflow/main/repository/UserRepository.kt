package com.qflow.main.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import android.content.ContentValues.TAG
import android.util.Log
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.qflow.main.core.BaseRepository
import com.qflow.main.core.Failure
import com.qflow.main.domain.adapters.UserAdapter
import com.qflow.main.domain.local.database.AppDatabase
import com.qflow.main.domain.local.database.user.UserDB
import com.qflow.main.usecases.Either
import com.qflow.main.domain.server.models.UserServerModel


/**
 * UserRepository, connects with firebase and the database to gets us what we need related
 * to the user
 * */
interface UserRepository {
    fun createUser(
        username: String, selectedPass: String, email: String, repeatPass: String,
        nameLastName: String
    ): Either<Failure, Long>

    fun signIn(email: String, pass: String)
    fun currentUserauth(): FirebaseUser?
    fun checkUser(): Either<Failure, Any>
    fun signOut()

    class General
    constructor(
        private val appDatabase: AppDatabase,
        val userAdapter: UserAdapter,
        val firebasedb: FirebaseFirestore
    ) : BaseRepository(), UserRepository {

        override fun createUser(
            username: String,
            selectedPass: String,
            email: String,
            repeatPass: String,
            nameLastName: String
        ): Either<Failure, Long> {
            //TODO move validation to UseCase

            val userMap =
                UserServerModel(username, selectedPass, email, nameLastName).createMap()
            //Storing into Firestore
            firebasedb.collection("users")
                .add(userMap)
                .addOnSuccessListener(OnSuccessListener<DocumentReference> { documentReference ->
                    Log.d(
                        TAG,
                        "DocumentSnapshot added with ID: " + documentReference.id
                    )
                    //Storing basic user into Local DB
                    val localUser = UserDB(id_firebase = "", username = username)
                    appDatabase.userDatabaseDao.insert(localUser)
                    //Todo: Ruben: mirar como gestionar el retorno asincrono
//                        return@OnSuccessListener Either.Right(123L)
                    //TODO do this comprobation
                    //id = appDatabase.userDatabaseDao.correctUser(localUser.username)?.userId
                })
                .addOnFailureListener(OnFailureListener { e ->
                    Log.w(
                        TAG,
                        "Error adding document",
                        e
                    )
                })
            return Either.Right(1L)
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
