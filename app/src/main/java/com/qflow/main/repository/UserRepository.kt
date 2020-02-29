package com.qflow.main.repository

import com.google.firebase.firestore.FirebaseFirestore
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
    fun createUser(username: String, selectedPass: String, email: String, repeatPass: String,
                   nameLastName: String): Either<Failure, Long>

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
            val user = UserDB(
                id_firebase = selectedPass,
                username = username
            )


            appDatabase.userDatabaseDao.insert(user)
            val id = appDatabase.userDatabaseDao.correctUser(user.username, user.password)?.userId
            return if (id!= null) {
                Either.Right(id)
            } else {
                Either.Left(Failure.NullResult())
            }
        }


    }
}
