package com.qflow.main.usecases.queue

import com.qflow.main.core.Failure
import com.qflow.main.repository.QueueRepository
import com.qflow.main.usecases.Either
import com.qflow.main.usecases.UseCase
import com.qflow.main.utils.enums.ValidationFailureType
import kotlinx.coroutines.CoroutineScope

class JoinQueue (private val queueRepository: QueueRepository) :
UseCase<Unit, JoinQueue.Params, CoroutineScope>() {
    /*
    private fun validQueue(id_queue:String): Either<Failure, Unit> {
        //We need: capacity and num_datos of queue_user with that id_queue

        return if (capacity.toInt() > 0)
            Either.Right(Unit)
        else
            Either.Left(Failure.ValidationFailure(ValidationFailureType.FULL_CAPACITY))
    }*/

    class Params(
       val id_queue: String, val id_user: String
    )

    override suspend fun run(params: JoinQueue.Params): Either<Failure, Unit> {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
         queueRepository.joinQueue(params.id_queue, params.id_user)
            return Either.Right(Unit)
        }

}