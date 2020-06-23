package com.qflow.main.usecases.queue

import androidx.annotation.IntegerRes
import com.google.gson.Gson
import com.qflow.main.core.Failure
import com.qflow.main.domain.adapters.QueueAdapter
import com.qflow.main.domain.local.SharedPrefsRepository
import com.qflow.main.repository.QueueRepository
import com.qflow.main.usecases.Either
import com.qflow.main.usecases.UseCase
import kotlinx.coroutines.CoroutineScope

class JoinQueue(private val queueRepository: QueueRepository,
                private val sharedPrefsRepository: SharedPrefsRepository,
                private val queueAdapter: QueueAdapter
) :
    UseCase<Int, JoinQueue.Params, CoroutineScope>() {

    override suspend fun run(params: Params): Either<Failure, Int> {
        //TODO change id to get with shared
        return when (val res = queueRepository.joinQueue(params.joinCode,
            sharedPrefsRepository.getUserToken().toString())) {
            is Either.Left -> Either.Left(res.a)
            is Either.Right -> {
                val idQueue = queueAdapter.jsonStringToQueueId(res.b)

                Either.Right(idQueue)
            }
        }
    }
    class Params(
        val joinCode: Int
    )

}
