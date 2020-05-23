package com.qflow.main.usecases.queue

import com.qflow.main.core.Failure
import com.qflow.main.domain.local.SharedPrefsRepository
import com.qflow.main.repository.QueueRepository
import com.qflow.main.usecases.Either
import com.qflow.main.usecases.UseCase
import kotlinx.coroutines.CoroutineScope

class JoinQueue(private val queueRepository: QueueRepository,
                private val sharedPrefsRepository: SharedPrefsRepository
) :
    UseCase<String, JoinQueue.Params, CoroutineScope>() {

    override suspend fun run(params: Params): Either<Failure,String> {
        //TODO change id to get with shared
        return when (val res = queueRepository.joinQueue(params.joinCode, params.token)) {
            is Either.Left -> Either.Left(res.a)
            is Either.Right -> {
                sharedPrefsRepository.putUserToken(res.b)
                Either.Right(res.b)
            }
        }
    }
    class Params(
        val joinCode: Int,
        val token: String
    )

}
