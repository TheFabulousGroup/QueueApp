package com.qflow.main.usecases.queue

import com.qflow.main.core.Failure
import com.qflow.main.domain.local.SharedPrefsRepository
import com.qflow.main.repository.QueueRepository
import com.qflow.main.usecases.Either
import com.qflow.main.usecases.UseCase
import com.qflow.main.utils.enums.ValidationFailureType
import kotlinx.coroutines.CoroutineScope
import com.qflow.main.domain.adapters.QueueAdapter


/**
 * UseCaseCreateUserInDatabase
 * */
class CreateQueue(
    private val queueRepository: QueueRepository,
    private val prefsRepository: SharedPrefsRepository
) :
    UseCase<String, CreateQueue.Params, CoroutineScope>() {
    override suspend fun run(params: Params): Either<Failure, String> {

        return when (val result = validQueue(params.capacity)) {
            is Either.Left -> Either.Left(result.a)
            is Either.Right -> {
                when (val res = queueRepository.createQueue(
                    prefsRepository.getUserToken().toString(),
                    params.nameCreateQueue,
                    params.queueDescription,
                    params.capacity,
                    params.businessAssociated
                )) {
                    is Either.Left -> Either.Left(res.a)
                    is Either.Right -> {
                        val queueAdapt = QueueAdapter.jsonStringToQueue(res.b)
                        //prefsRepository.putUserToken(queueAdapt.token.toString())
                        Either.Right(res.b)
                    }
                }
            }
        }
    }

    private fun validQueue(capacity: Int): Either<Failure, Unit> {
        return if (capacity > 0)
            Either.Right(Unit)
        else
            Either.Left(Failure.ValidationFailure(ValidationFailureType.CAPACITY_TOO_SMALL))
    }

    class Params(
        val nameCreateQueue: String,
        val queueDescription: String,
        val capacity: Int,
        val businessAssociated: String
    )


}