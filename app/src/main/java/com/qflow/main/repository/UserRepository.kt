package com.qflow.main.repository

import com.qflow.main.domain.adapters.UserAdapter
import com.qflow.main.domain.server.ApiService
import com.qflow.main.usecases.Either
import com.qflow.main.core.BaseRepository
import com.qflow.main.core.Failure
import com.qflow.main.domain.local.database.AppDatabase
import com.qflow.main.domain.local.database.user.User


interface UserRepository {
    fun saveUser(username: String, selectedPass: String, email: String): Either<Failure, Long>

    class Local
    constructor(
        private val appDatabase: AppDatabase,
        val userAdapter: UserAdapter
    ) : BaseRepository(), UserRepository {

        override fun saveUser(
            username: String,
            selectedPass: String,
            email: String
        ): Either<Failure, Long> {
            val user = User(
                password = selectedPass,
                username = username,
                mail = email
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