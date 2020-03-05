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
    fun saveUser(selectedPass: String, email: String): Either<Failure, Long>
    class General
    //class Local
    constructor(
        private val appDatabase: AppDatabase,
        val userAdapter: UserAdapter

    ) : BaseRepository(), UserRepository {


        override fun saveUser(
            //username: String,
            selectedPass: String,
            selectedEmail: String
        ): Either<Failure, Long> {

            //)
           //fun sigIn(selectedEmail:String,selectedPass: String) {

               // val readData = FireBaseDataBase().getInstance()
                // val userFireBase = UserServerModel("",selectedEmail,selectedPass)
                // val userMap = userFire.createMap()
                // .addOnSuccessListener(OnSuccessListener<DocumentReference> { /*documentReference ->
                //                        Log.d(
                //                            TAG,
                //                            "DocumentSnapshot added with ID: " + documentReference.id
                //                        )*/
                //                    })
                //                    .addOnFailureListener(OnFailureListener { /*e ->
                //                        Log.w(
                //                            TAG,
                //                            "Error adding document",
                //                            e
                //                        )*/
                //                    })
                //                //Storing basic user into Local DB
                //        val localUser = UserDB(id_firebase = "", username = username)
                //        appDatabase.userDatabaseDao.correctUser(localUser)
                //        id = appDatabase.userDatabaseDao.correctUser(localUser.username, localUser.password)?.userId
           // }

         //

            val user = UserDB(
                password = selectedPass,
                mail = selectedEmail
            )
            appDatabase.userDatabaseDao.insert(user)
            val id = appDatabase.userDatabaseDao.correctUser(user.mail,user.password)?.userId
            return if (id!= null) {
                Either.Right(id)
            } else {
                Either.Left(Failure.NullResult())
            }
        }


    }
}
/*
* private lateinit var fb
* */