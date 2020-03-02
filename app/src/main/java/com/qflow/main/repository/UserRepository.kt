package com.qflow.main.repository

import com.qflow.main.domain.adapters.UserAdapter
import com.qflow.main.usecases.Either
import com.qflow.main.core.BaseRepository
import com.qflow.main.core.Failure
import com.qflow.main.domain.local.database.AppDatabase
import com.qflow.main.domain.local.database.user.UserDB
import java.lang.NullPointerException

//import com.google.firebase.firestore.FirebaseFirestore




/**
 * UserRepository, connects with firebase and the database to gets us what we need related
 * to the user
 * */
interface UserRepository {
    fun saveUser(selectedPass: String, email: String): Either<Failure, Long>

    //class General
    class Local
    constructor(
        private val appDatabase: AppDatabase,
        val userAdapter: UserAdapter
       // val firebasedb: FirebaseFirestore
    ) : BaseRepository(), UserRepository {


        override fun saveUser(
            //username: String,
            selectedPass: String,
            email: String
        ): Either<Failure, Long> {
            //val user = UserDB(
            // id_firebase = selectedPass
            // username = username
            // mail = mail
            //)
            val user = UserDB(
                password = selectedPass,
                mail = email
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
