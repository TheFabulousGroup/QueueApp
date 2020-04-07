package com.qflow.main.repository

import com.google.firebase.auth.FirebaseAuth
import android.content.ContentValues.TAG
import android.util.Log
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
    suspend fun createUser(
        username: String, selectedPass: String, email: String,
        nameLastName: String
    ): Either<Failure, String>

    suspend fun signIn(email: String, pass: String): Either<Failure, String>
    suspend fun createAdmin(
        username: String,
        selectedPass: String,
        selectedEmail: String,
        selectedNameLastName: String
    ): Either<Failure,String>


    class General
    constructor(
        private val appDatabase: AppDatabase,
        private val userAdapter: UserAdapter,
        private val firebasedb: FirebaseFirestore,
        private val firebaseAuth: FirebaseAuth
    ) : BaseRepository(), UserRepository {

        override suspend fun createUser(
            username: String,
            selectedPass: String,
            email: String,
            nameLastName: String
        ): Either<Failure, String> {
            val userMap =
                UserServerModel(
                    username,
                    selectedPass,
                    email,
                    nameLastName,
                    false
                ).createMap()
            //Storing into Firestore
            val taskFirebase = firebasedb.collection("users")
                .add(userMap)

            val resultFirebase = firebaseRequest(taskFirebase) {
                it.id
            }

            return when(resultFirebase){
                is Either.Left -> resultFirebase
                is Either.Right -> {
                    val idFire = taskFirebase.result?.id
                    if (idFire == null)
                        Either.Left(Failure.NetworkConnection)
                    else {
                        Log.d(
                            TAG,
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
            }
        }

        override suspend fun signIn(email: String, pass: String): Either<Failure, String> {
            val task = firebaseAuth.signInWithEmailAndPassword(email, pass)
            return firebaseRequest(task){
                it.user?.uid ?: "null"
            }
        }

        override suspend fun createAdmin(
            username: String,
            selectedPass: String,
            selectedEmail: String,
            selectedNameLastName: String
        ): Either<Failure, String> {
            val adminMap =
                UserServerModel(
                    username,
                    selectedPass,
                    selectedEmail,
                    selectedNameLastName,
                    true
                ).createMap()
            //Storing into Firestore
            val taskFirebase = firebasedb.collection("users")
                .add(adminMap)

            val resFirebase = firebaseRequest(taskFirebase) {
                it.id
            }
            return when (resFirebase) {
                is Either.Left -> resFirebase
                is Either.Right -> {
                    val idFire = taskFirebase.result?.id
                    if (idFire == null)
                        Either.Left(Failure.NetworkConnection)
                    else {
                        Log.d(
                            TAG,
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
            }
        }
    }
}