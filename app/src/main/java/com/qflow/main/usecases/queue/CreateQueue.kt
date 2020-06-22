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

        return when (val result = validQueue(
            params.nameCreateQueue,
            params.queueDescription,
            params.capacity,
            params.businessAssociated,
            params.avgServiceTime
        )) {
            is Either.Left -> Either.Left(result.a)
            is Either.Right -> {
                when (val res = queueRepository.createQueue(
                    prefsRepository.getUserToken().toString(),
                    params.nameCreateQueue,
                    params.queueDescription,
                    params.capacity,
                    params.businessAssociated,
                    params.avgServiceTime
                )) {
                    is Either.Left -> Either.Left(res.a)
                    is Either.Right -> {
                        Either.Right(res.b)
                    }
                }
            }
        }
    }

    private fun validQueue(
        nameCreateQueue: String,
        queueDescription: String,
        capacity: Int,
        businessAssociated: String,
        avgServiceTime: Int
    ): Either<Failure, Unit> {
        return when(capacity.toString().isEmpty() || nameCreateQueue.isEmpty() ||
                queueDescription.isEmpty() || businessAssociated.isEmpty() ||
                avgServiceTime.toString().isEmpty()) {
                true -> Either.Left(Failure.ValidationFailure(ValidationFailureType.FIELDS_EMPTY))
                false->{
                    when(capacity <= 0 ) {
                        true -> Either.Left(Failure.ValidationFailure(ValidationFailureType.CAPACITY_TOO_SMALL))
                        false -> when(avgServiceTime <=0){
                            true -> Either.Left(Failure.ValidationFailure(ValidationFailureType.AVG_TIME))
                            false ->Either.Right(Unit)
                        }
                    }
                }
        }
    }

    class Params(
        val nameCreateQueue: String,
        val queueDescription: String,
        val capacity: Int,
        val businessAssociated: String,
        val avgServiceTime: Int
    )


}