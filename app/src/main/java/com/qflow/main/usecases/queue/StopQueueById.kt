package com.qflow.main.usecases.queue

import com.qflow.main.core.Failure
import com.qflow.main.domain.local.models.Queue
import com.qflow.main.repository.QueueRepository
import com.qflow.main.usecases.Either
import com.qflow.main.usecases.UseCase
import com.qflow.main.utils.enums.ValidationFailureType
import kotlinx.coroutines.CoroutineScope

class StopQueueById(private val queueRepository: QueueRepository) :
    UseCase<Queue, StopQueueById.Params, CoroutineScope>() {
    override suspend fun run(params: StopQueueById.Params): Either<Failure, Queue> {
        return when (
            val result = queueRepository.stopQueue(params.idQueue)
            )
        {
            is Either.Left -> Either.Left(result.a)
            is Either.Right -> Either.Right(result.b)
        }
    }
    //TODO return "something"
    private fun tryAdvance(currentPos:Int): Either<Failure, Unit>{
        return if(currentPos) Either.Left(Failure.ValidationFailure(ValidationFailureType.QUEUE_ADVANCE_STOP))
        else Either.Right(Unit)
    }
    class Params(
        val idQueue: Int,val currentPos:Int
    )
}