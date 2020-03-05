package com.qflow.main.repository

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
import com.qflow.main.domain.server.models.UserServerModel
import com.qflow.main.usecases.Either


/**
 * UserRepository, connects with firebase and the database to gets us what we need related
 * to the user
 * */
interface UserRepository {
    fun createUser(
        username: String, selectedPass: String, email: String, repeatPass: String,
        nameLastName: String
    ): Either<Failure, Long>

    fun validPassword(selectedPass: String, repeatPass: String): Boolean

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
            if (validPassword(selectedPass, repeatPass)) {
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
            }
        }

        override fun validPassword(selectedPass: String, repeatPass: String): Boolean {
            return selectedPass == repeatPass
        }
    }


}
