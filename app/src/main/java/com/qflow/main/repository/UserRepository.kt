package com.qflow.main.repository

import com.qflow.main.domain.adapters.UserAdapter
import com.qflow.main.domain.server.ApiService
import com.qflow.main.usecases.Either
import com.qflow.main.core.BaseRepository
import com.qflow.main.core.Failure


interface UserRepository {
    fun signup(firstname: String, lastname: String, email: String): Either<Failure, Unit>

    class Network
    constructor(
        val service: ApiService,
        val userAdapter: UserAdapter
    ) : BaseRepository(), UserRepository {

        override fun signup(
            firstname: String,
            lastname: String,
            email: String
        ): Either<Failure, Unit> {

            return Either.Right(Unit)

        }


    }
}
