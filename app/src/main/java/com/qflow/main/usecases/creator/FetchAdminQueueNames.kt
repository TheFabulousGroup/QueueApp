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
class FetchAdminQueueNames(private val queueRepository: QueueRepository) :
    UseCase<ArrayList<Queue>, FetchAdminQueueNames.Params, CoroutineScope>() {
    override suspend fun run(params: Params): Either<Failure, ArrayList<Queue>> {

        return when (val result = queueRepository.fetchAdminActiveQueues(params.iduser)){
            is Either.Left -> Either.Left(result.a)
            is Either.Right -> Either.Right(result.b)
        }


  /*      var namesArray = ArrayList<String>()
        queueArray.forEach(){
            it.name?.let { it1 -> namesArray.add(it1) }
        }

        return if(namesArray.size < 0)
             Either.Left(Failure.NetworkConnection)
                else Either.Right(namesArray)
                */
        }


    class Params(
        val iduser: String
    )

}