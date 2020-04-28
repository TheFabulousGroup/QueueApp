package com.qflow.main.repository

import com.google.firebase.auth.FirebaseAuth
import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.qflow.main.core.BaseRepository
import com.qflow.main.core.Failure
import com.qflow.main.core.extensions.empty
import com.qflow.main.domain.adapters.UserAdapter
import com.qflow.main.domain.local.database.AppDatabase
import com.qflow.main.domain.local.database.user.UserDB
import com.qflow.main.domain.server.ApiService
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
    ): Either<Failure, String>


    class General
    constructor(
        private val userAdapter: UserAdapter,
        private val apiService: ApiService
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

            return request(apiService.postCreateUser(userMap.toString()), {
                it
            }, String.empty())
        }

        override suspend fun signIn(email: String, pass: String): Either<Failure, String> {
            val task = HashMap<String, String>()
            task["email"] = email
            task["pass"] = pass
            return request(apiService.postLoginUser(task.toString()), {
                it
            }, String.empty())
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

            return request(apiService.postCreateUser(adminMap.toString()), {
                it
            }, String.empty())
        }
    }
}