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
    ): Either<Failure, String>
    fun signIn(email: String, pass: String): Either<Failure, String>

    class General
    constructor(
        private val appDatabase: AppDatabase,
        private val userAdapter: UserAdapter,
        private val firebasedb: FirebaseFirestore,
        private val firebaseAuth: FirebaseAuth
    ) : BaseRepository(), UserRepository {

        override fun createUser(
            username: String,
            selectedPass: String,
            email: String,
            repeatPass: String,
            nameLastName: String
        ): Either<Failure, String> {
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
            return Either.Right("")
        }

        override fun signIn(email: String,pass: String): Either<Failure, String>{

            firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
//                return if (task.isSuccessful) {
//                    Log.d(TAG, "You´re signin in succesfully ")
//                    Either.Right(firebaseAuth.currentUser!!.uid)
//
//                } else {
//                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
//                    Either.Left(Failure.NetworkConnection)
//                }
            }
            return Either.Right("")
        }
    }
}
