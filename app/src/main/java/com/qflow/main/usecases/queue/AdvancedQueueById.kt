package com.qflow.main.usecases.queue

import com.qflow.main.core.Failure
import com.qflow.main.domain.local.SharedPrefsRepository
import com.qflow.main.domain.local.models.Queue
import com.qflow.main.repository.QueueRepository
import com.qflow.main.usecases.Either
import com.qflow.main.usecases.UseCase
import com.qflow.main.utils.enums.ValidationFailureType
import kotlinx.coroutines.CoroutineScope
import java.sql.Timestamp

class AdvancedQueueById(
    val queueRepository: QueueRepository,
    private val prefsRepository: SharedPrefsRepository
) :
    UseCase<Queue, AdvancedQueueById.Params, CoroutineScope>() {
    override suspend fun run(params: AdvancedQueueById.Params): Either<Failure, Queue> {
        return when (val resL = isLocked(params.isLock)) {
            is Either.Left -> Either.Left(resL.a)
            is Either.Right -> {
                when (val resD = isDateFinished(params.dateFinish)) {
                    is Either.Left -> Either.Left(resD.a)
                    is Either.Right -> {
                        when (val resCapacity = fullCapacity(params.numPersons, params.capacity)) {
                            is Either.Left -> Either.Left(resCapacity.a)
                            is Either.Right -> {
                                when (
                                    val result = queueRepository.advanceQueue(
                                        params.idQueue,
                                        prefsRepository.getUserToken().toString()
                                    )
                                    ) {
                                    is Either.Left -> Either.Left(result.a)
                                    is Either.Right -> Either.Right(result.b)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun isLocked(isLock: Boolean): Either<Failure, Unit> {
        return if (isLock) Either.Left(Failure.ValidationFailure(ValidationFailureType.QUEUE_LOCK))
        else Either.Right(Unit)
    }

    private fun isDateFinished(dateFinish: Timestamp): Either<Failure, Unit> {
        return if (dateFinish != null) Either.Left(Failure.ValidationFailure(ValidationFailureType.QUEUE_CLOSE))
        else Either.Right(Unit)
    }

    private fun fullCapacity(numPersons: Int, capacity: Int): Either<Failure, Unit>{
        return if(numPersons > capacity)  Either.Left(Failure.ValidationFailure(ValidationFailureType.FULL_CAPACITY))
        else Either.Right(Unit)
    }
    class Params(
        val idQueue: Int,
        val isLock: Boolean,
        val dateFinish: Timestamp,
        val numPersons: Int,
        val capacity: Int
    )
}