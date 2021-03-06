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
        return when (
            val result = queueRepository.advanceQueue(
                params.idQueue,
                prefsRepository.getUserToken().toString()
            )
            ) {
            is Either.Left -> Either.Left(Failure.CantAdvanceQueue)
            is Either.Right -> Either.Right(result.b)
        }
    }

    class Params(
        val idQueue: Int
    )
}