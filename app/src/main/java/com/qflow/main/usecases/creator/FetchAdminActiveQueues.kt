package com.qflow.main.usecases.creator

import com.qflow.main.core.Failure
import com.qflow.main.domain.local.models.Queue
import com.qflow.main.repository.QueueRepository
import com.qflow.main.usecases.Either
import com.qflow.main.usecases.UseCase
import kotlinx.coroutines.CoroutineScope

/**
 * UseCaseCreateUserInDatabase
 * */
class FetchAdminActiveQueues(private val queueRepository: QueueRepository) :
    UseCase<List<Queue>, FetchAdminActiveQueues.Params, CoroutineScope>() {
    override suspend fun run(params: Params): Either<Failure, List<Queue>> {

        return when (
            val result = queueRepository.fetchAdminQueuesRepository(params.iduser, true)
            ) {
            is Either.Left -> Either.Left(result.a)
            is Either.Right -> Either.Right(result.b)
        }
    }


    class Params(
        val iduser: String
    )

}