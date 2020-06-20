package com.qflow.main.usecases.queue

import com.qflow.main.core.Failure
import com.qflow.main.domain.local.models.Queue
import com.qflow.main.repository.QueueRepository
import com.qflow.main.usecases.Either
import com.qflow.main.usecases.UseCase
import com.qflow.main.utils.enums.ValidationFailureType
import kotlinx.coroutines.CoroutineScope

class ResumeQueueById(private val queueRepository: QueueRepository) :
    UseCase<Queue, ResumeQueueById.Params, CoroutineScope>() {
    override suspend fun run(params: ResumeQueueById.Params): Either<Failure, Queue> {
        return when ( val resultResume = tryToResumeQueue(params.isLocked)) {
            is Either.Left -> Either.Left(resultResume.a)
            is Either.Right -> {
                when (val result = queueRepository.resumeQueue(params.idQueue)) {
                    is Either.Left -> Either.Left(result.a)
                    is Either.Right -> Either.Right(result.b)
                }
            }
        }
    }

    private fun tryToResumeQueue(isLocked:Boolean): Either<Failure, Unit> {
        return if(!isLocked) Either.Left(Failure.ValidationFailure(ValidationFailureType.QUEUE_RESUME))
        else Either.Right(Unit)
    }

    class Params(
        val idQueue: Int, val isLocked:Boolean
    )
}