package com.qflow.main.usecases.creator

import com.qflow.main.core.Failure
import com.qflow.main.domain.local.models.Queue
import com.qflow.main.repository.QueueRepository
import com.qflow.main.usecases.Either
import com.qflow.main.usecases.UseCase
import kotlinx.coroutines.CoroutineScope

class FetchAdminNotActiveQueues(private val queueRepository: QueueRepository) :
    UseCase<List<Queue>, FetchAdminNotActiveQueues.Params, CoroutineScope>() {
    override suspend fun run(params: Params): Either<Failure, List<Queue>> {

        return when (
            val result =
                queueRepository.fetchAdminQueuesRepository(params.idUser, false)
            ) {
            is Either.Left -> Either.Left(result.a)
            is Either.Right -> Either.Right(result.b)
        }

    }

    class Params(
        val idUser: String
    )
}