package com.qflow.main.repository

import com.google.gson.Gson
import com.qflow.main.core.BaseRepository
import com.qflow.main.core.Failure
import com.qflow.main.core.extensions.empty
import com.qflow.main.domain.adapters.UserAdapter
import com.qflow.main.domain.server.ApiService
import com.qflow.main.usecases.Either


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
            val userMap = HashMap<String,String>()
            userMap["username"] = username
            userMap["email"] = email
            userMap["password"] = selectedPass
            userMap["nameLastName"] = nameLastName
            return request(apiService.postCreateUser(Gson().toJson(userMap),false), {
                it
            }, String.empty())
        }

        override suspend fun signIn(email: String, pass: String): Either<Failure, String> {
            val task = HashMap<String, String>()
            task["email"] = email
            task["pass"] = pass
            return request(apiService.postLoginUser(Gson().toJson(task),false,task.toString(),task.toString()), {
                it
            }, String.empty())
        }

        override suspend fun createAdmin(
            username: String,
            selectedPass: String,
            selectedEmail: String,
            selectedNameLastName: String
        ): Either<Failure, String> {
            val adminMap = HashMap<String,String>()
            adminMap["username"] = username
            adminMap["email"] = selectedEmail
            adminMap["password"] = selectedPass
            adminMap["nameLastName"] = selectedNameLastName
            return request(apiService.postCreateUser(Gson().toJson(adminMap),true), {
                it
            }, String.empty())
        }
    }
}