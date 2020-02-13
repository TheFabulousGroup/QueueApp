package com.queueapp.main.repository

import com.queueapp.main.domain.adapters.UserAdapter
import com.queueapp.main.domain.server.ApiService
import com.queueapp.main.usecases.Either
import org.visionapp.myopia.kotlin.core.BaseRepository
import org.visionapp.myopia.kotlin.core.Failure


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