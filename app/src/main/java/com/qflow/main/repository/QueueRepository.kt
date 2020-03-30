package com.qflow.main.repository

import android.content.ContentValues
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.qflow.main.core.BaseRepository
import com.qflow.main.core.Failure
import com.qflow.main.domain.adapters.QueueAdapter
import com.qflow.main.domain.local.database.AppDatabase
import com.qflow.main.domain.server.models.QueueServerModel
import com.qflow.main.usecases.Either

interface QueueRepository {

    fun createQueue(
        name: String,
        description: String,
        capacity: String,
        business_associated: String
    ): Either<Failure, String>

    class General
    constructor(
        private val appDatabase: AppDatabase,       //todo Add local DB for queue ?
        private val queueAdapter: QueueAdapter,
        private val firebasedb: FirebaseFirestore,
        private val firebaseAuth: FirebaseAuth
    ) : BaseRepository(), QueueRepository {

        override fun createQueue(
            name: String,
            description: String,
            capacity: String,
            business_associated: String
            /*date_created: String,
            date_finished: String,
            is_locked: Boolean*/
        ): Either<Failure, String> {

            val queueMap =
                QueueServerModel(name, description, capacity.toInt(), business_associated).createMap()
            //Storing into Firestore
            val taskFirebase = firebasedb.collection("queue")
                .add(queueMap)

            return if(taskFirebase.isSuccessful){
                val idFire = taskFirebase.result?.id
                if(idFire == null)
                    Either.Left(Failure.NetworkConnection)
                else {
                    Log.d(
                        ContentValues.TAG,
                        "DocumentSnapshot added with ID: " + taskFirebase.result?.id
                    )
                    /*val localUser =
                        taskFirebase.result?.id?.let {
                            UserDB(
                                id_firebase = it,
                                username = username
                            )
                        }
                    if (localUser != null) {
                        appDatabase.userDatabaseDao.insert(localUser)
                    }*/
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
        }

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