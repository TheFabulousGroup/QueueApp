package com.qflow.main.usecases.queue

import com.qflow.main.core.Failure
import com.qflow.main.domain.adapters.QueueAdapter
import com.qflow.main.domain.local.SharedPrefsRepository
import com.qflow.main.domain.local.models.Queue
import com.qflow.main.repository.QueueRepository
import com.qflow.main.usecases.Either
import com.qflow.main.usecases.UseCase
import kotlinx.coroutines.CoroutineScope

/**
 * UseCaseCreateUserInDatabase
 * */
class FetchQueuesByUser(private val prefsRepository: SharedPrefsRepository,
                        private val queueRepository: QueueRepository) :
    UseCase<List<Queue>, FetchQueuesByUser.Params, CoroutineScope>() {
    override suspend fun run(params: Params): Either<Failure, List<Queue>> {

        return when (
            val result = queueRepository.fetchQueuesByUser(prefsRepository.getUserToken().toString(), params.expand, params.finished)
            )
        {
            is Either.Left -> Either.Left(result.a)
            is Either.Right -> Either.Right(result.b)
        }
    }

    class Params(
        val expand: String?,
        val finished: Boolean?
    )

}