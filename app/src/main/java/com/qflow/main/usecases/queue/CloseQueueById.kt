package com.qflow.main.usecases.queue

import com.qflow.main.core.Failure
import com.qflow.main.domain.local.models.Queue
import com.qflow.main.repository.QueueRepository
import com.qflow.main.usecases.Either
import com.qflow.main.usecases.UseCase
import com.qflow.main.utils.enums.ValidationFailureType
import kotlinx.coroutines.CoroutineScope
import java.sql.Timestamp

class CloseQueueById (private val queueRepository: QueueRepository) :
    UseCase<Queue, CloseQueueById.Params, CoroutineScope>() {

    override suspend fun run(params: CloseQueueById.Params): Either<Failure, Queue> {
        return when (val result = queueRepository.closeQueue(params.idQueue)
            ) {
            is Either.Left -> Either.Left(result.a)
            is Either.Right -> Either.Right(result.b)
        }

    }

    class Params(
        val idQueue: Int
    )
}