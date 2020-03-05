package com.qflow.main.repository

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
    fun createUser(username: String, selectedPass: String, email: String, repeatPass: String,
                   nameLastName: String): Either<Failure, Long>
    fun  validPassword(selectedPass: String, repeatPass: String): Boolean

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
            var id = null
            var TEMPORAL = false

            if(validPassword(selectedPass, repeatPass)) {
                val userFireBase = UserServerModel(username, selectedPass, email, nameLastName)
                val userMap = userFireBase.createMap()
                //Storing into Firestore TODO
                firebasedb.collection("users")
                    .add(userMap)
                    .addOnSuccessListener(OnSuccessListener<DocumentReference> { TEMPORAL = false;/*documentReference ->
                        Log.d(  TODO Añadir mensaje de acierto y variable para el right
                            TAG,
                            "DocumentSnapshot added with ID: " + documentReference.id
                        )*/
                    })
                    .addOnFailureListener(OnFailureListener { TEMPORAL = false/*e ->
                        Log.w(  TODO añadir mensaje de error y variable para el left
                            TAG,
                            "Error adding document",
                            e
                        )*/
                    })
                //Storing basic user into Local DB
                val localUser = UserDB(id_firebase = "", username = username)
                appDatabase.userDatabaseDao.insert(localUser)
                //id = appDatabase.userDatabaseDao.correctUser(localUser.username)?.userId
            }

            return if (TEMPORAL/*id!= null*/) {
                Either.Right(1)
            } else {
                Either.Left(Failure.NullResult())
            }
        }
        override fun validPassword(selectedPass: String, repeatPass: String): Boolean{
            return selectedPass == repeatPass
        }
    }




}
