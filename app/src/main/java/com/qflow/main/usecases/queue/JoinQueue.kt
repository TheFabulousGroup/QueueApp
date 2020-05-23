/*package com.qflow.main.usecases.queue

import com.qflow.main.core.Failure
import com.qflow.main.repository.QueueRepository
import com.qflow.main.usecases.Either
import com.qflow.main.usecases.UseCase
import kotlinx.coroutines.CoroutineScope

class JoinQueue(private val queueRepository: QueueRepository) :
    UseCase<Int, JoinQueue.Params, CoroutineScope>() {

    override suspend fun run(params: Params): Either<Failure, Int> {
        //TODO change id to get with shared
        return when (val res = queueRepository.joinQueue(params.id_queue)) {
            is Either.Left -> res
            is Either.Right -> {
                if (res.b.id != null)
                    Either.Right(res.b.id)
                else
                    Either.Left(Failure.NullResult())
            }
        }


    }

    class Params(
        val id_queue: Int
    )

}*/
