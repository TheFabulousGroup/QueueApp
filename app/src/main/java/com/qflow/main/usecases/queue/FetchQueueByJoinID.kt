package com.qflow.main.usecases.queue

import com.qflow.main.core.Failure
import com.qflow.main.domain.local.SharedPrefsRepository
import com.qflow.main.domain.local.models.Queue
import com.qflow.main.repository.QueueRepository
import com.qflow.main.usecases.Either
import com.qflow.main.usecases.UseCase
import kotlinx.coroutines.CoroutineScope

class FetchQueueByJoinID(
    private val queueRepository: QueueRepository,
    private val sharedPrefsRepository: SharedPrefsRepository
) :
    UseCase<FetchQueueByJoinID.ReturnParams, FetchQueueByJoinID.Params, CoroutineScope>() {
    override suspend fun run(params: Params): Either<Failure, ReturnParams> {

        return when (
            val result = queueRepository.fetchQueueByJoinId(params.idJoin)
            ) {
            is Either.Left -> Either.Left(result.a)
            is Either.Right -> {
                when (val res = queueRepository.fetchQueuesByUser(
                    sharedPrefsRepository.getUserToken()!!,
                    "user",
                    false
                )) {
                    is Either.Left -> Either.Left(res.a)
                    is Either.Right -> {
                        if (res.b.isEmpty())
                            Either.Right(ReturnParams(result.b, false))
                        else {
                            if(res.b.stream().anyMatch { it.id == result.b.id })
                                Either.Right(ReturnParams(result.b, true))
                            else
                                Either.Right(ReturnParams(result.b, false))
                        }
                    }
                }
            }
        }
    }

    class Params(
        val idJoin: Int
    )

    class ReturnParams(
        val queue: Queue,
        val alreadyInQueue: Boolean
    )
}