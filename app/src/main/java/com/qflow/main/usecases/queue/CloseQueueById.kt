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
        return when ( val resD = tryCloseWhenIsClosed(params.dateFinish)){
            is Either.Left -> Either.Left(resD.a)
            is Either.Right -> {
                when (val resAdClose = tryAdvanceCloseQueue(params.numPerson)) {
                    is Either.Left -> Either.Left(resAdClose.a)
                    is Either.Right -> {
                        when (val result = queueRepository.closeQueue(params.idQueue)
                            ) {
                            is Either.Left -> Either.Left(result.a)
                            is Either.Right -> Either.Right(result.b)
                        }
                    }
                }
            }
        }
    }

    private fun tryAdvanceCloseQueue(numPerson:Int): Either<Failure, Unit>{
        return if(numPerson > 0) Either.Left(Failure.ValidationFailure(ValidationFailureType.QUEUE_ADVANCE_CLOSE))
        else Either.Right(Unit)
    }

    private fun tryCloseWhenIsClosed(dateFinish:Timestamp): Either<Failure, Unit>{
        return if (dateFinish != null) Either.Left(Failure.ValidationFailure(ValidationFailureType.QUEUE_CLOSE_CLOSED))
        else Either.Right(Unit)
    }
    class Params(
        val idQueue: Int, val numPerson: Int, val dateFinish:Timestamp
    )
}