package com.qflow.main.repository

import android.content.ContentValues
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.qflow.main.core.BaseRepository
import com.qflow.main.core.Failure
import com.qflow.main.domain.adapters.UserAdapter
import com.qflow.main.domain.local.database.AppDatabase
import com.qflow.main.domain.local.database.user.UserDB
import com.qflow.main.domain.server.models.UserServerModel
import com.qflow.main.usecases.Either

interface QueueRepository {

    //Add repository functions declaration
    //Examples from UserRepo: fun signIn(email: String, pass: String): Either<Failure, String>

    class General
    constructor(
        private val appDatabase: AppDatabase,
        //private val queueAdapter: QueueAdapter,       //Todo add QueueAdapter
        private val firebasedb: FirebaseFirestore,
        private val firebaseAuth: FirebaseAuth
    ) : BaseRepository(), UserRepository {

        /*override fun createUser(  //Todo create Queue (CREATORS DEBUG)
            username: String,
            selectedPass: String,
            email: String,
            nameLastName: String
        ): Either<Failure, String> {

            val userMap =
                UserServerModel(username, selectedPass, email, nameLastName).createMap()
            //Storing into Firestore
            val taskFirebase = firebasedb.collection("users")
                .add(userMap)

            return if(taskFirebase.isSuccessful){
                val idFire = taskFirebase.result?.id
                if(idFire == null)
                    Either.Left(Failure.NetworkConnection)
                else {
                    Log.d(
                        ContentValues.TAG,
                        "DocumentSnapshot added with ID: " + taskFirebase.result?.id
                    )
                    val localUser =
                        taskFirebase.result?.id?.let {
                            UserDB(
                                id_firebase = it,
                                username = username
                            )
                        }
                    if (localUser != null) {
                        appDatabase.userDatabaseDao.insert(localUser)
                    }
                    Either.Right(idFire)
                }
            }
            else{
                Log.w(
                    ContentValues.TAG,
                    "Error adding document"
                )
                Either.Left(Failure.NetworkConnection)
            }
        }*/

        /*override fun signIn(email: String,pass: String): Either<Failure, String> {    //Todo joinQueue
            val task = firebaseAuth.signInWithEmailAndPassword(email, pass)
            return if (task.isSuccessful) {
                Log.d(TAG, "You´re signin in succesfully ")
                Either.Right(firebaseAuth.currentUser!!.uid)

            } else {
                Log.w(TAG, "createUserWithEmail:failure", task.exception)
                Either.Left(Failure.NetworkConnection)
            }

        }*/
    }

}