package com.qflow.main.usecases.queue

import com.qflow.main.core.Failure
import com.qflow.main.domain.local.models.Queue
import com.qflow.main.repository.QueueRepository
import com.qflow.main.usecases.Either
import com.qflow.main.usecases.UseCase
import kotlinx.coroutines.CoroutineScope

/**
 * UseCaseCreateUserInDatabase
 * */
class FetchQueueById(private val queueRepository: QueueRepository) :
    UseCase<Queue, FetchQueueById.Params, CoroutineScope>() {
    override suspend fun run(params: Params): Either<Failure, Queue> {

        return when (
            val result = queueRepository.fetchQueueById(params.idQueue)
            )
        {
            is Either.Left -> Either.Left(result.a)
            is Either.Right -> Either.Right(result.b)
        }
    }

    class Params(
        val idQueue: Int
    )

}