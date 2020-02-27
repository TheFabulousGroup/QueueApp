package com.qflow.main.core

import com.qflow.main.usecases.Either

/*
* @author  Iván Fernández Rico, Globalincubator
*/
abstract class BaseRepository
{
    fun <T, R> request(call: retrofit2.Call<T>, transform: (T) -> R, default: T): Either<Failure, R> {
        return try {
            val response = call.execute()
            when (response.isSuccessful) {
                true -> Either.Right(transform((response.body() ?: default)))
                false -> {
                    Either.Left(Failure.ServerErrorCode(response.code()))
                }
            }
        } catch (exception: Throwable) {
            Either.Left(Failure.ServerException(exception))
        }
    }
}