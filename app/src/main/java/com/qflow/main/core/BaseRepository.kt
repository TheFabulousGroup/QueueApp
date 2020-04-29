package com.qflow.main.core

import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseException
import com.google.firebase.functions.FirebaseFunctionsException
import com.qflow.main.usecases.Either
import kotlinx.coroutines.tasks.await
import retrofit2.Call
import java.lang.ClassCastException

abstract class BaseRepository
{

    suspend fun <T, R> firebaseRequest(call: Task<T>, transform: (T) -> R): Either<Failure, R> {
        var result: Either<Failure, R> = Either.Left(Failure.NullResult())
        return try {
            call.addOnSuccessListener {
                call.result?.let { res ->  result = Either.Right(transform(res))}
            }
            call.addOnFailureListener {
                result = try{
                    it as FirebaseFunctionsException
                    Either.Left(Failure.ServerErrorCode(it.code.ordinal))
                } catch(ex : ClassCastException){
                    Either.Left(Failure.ServerException(it))
                }
            }
            call.await()
            result
        } catch (exception: Throwable) {
            Either.Left(Failure.ServerException(exception))
        }
    }

    fun <T, R> request(call: Call<T>, transform: (T) -> R, default: T): Either<Failure,R> {
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