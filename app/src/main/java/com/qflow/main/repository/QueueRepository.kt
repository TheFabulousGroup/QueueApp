package com.qflow.main.repository

import android.util.Log
import com.google.gson.Gson
import com.qflow.main.core.BaseRepository
import com.qflow.main.core.Failure
import com.qflow.main.core.extensions.empty
import com.qflow.main.domain.adapters.QueueAdapter
import com.qflow.main.domain.local.SharedPrefsRepository
import com.qflow.main.domain.local.models.Queue
import com.qflow.main.domain.server.ApiService
import com.qflow.main.domain.server.models.QueueServerModel
import com.qflow.main.usecases.Either

interface QueueRepository {

    suspend fun createQueue(
        name: String,
        description: String,
        capacity: Int,
        business_associated: String
    ): Either<Failure, String>

    suspend fun joinQueue(idQueue: String): Either<Failure, Queue>
    suspend fun fetchQueueById(idQueue: Int): Either<Failure, Queue>
    suspend fun fetchQueuesByUser(expand: String?, locked: Boolean?): Either<Failure, List<Queue>>

    class General
    constructor(
        private val queueAdapter: QueueAdapter,
        private val apiService: ApiService,
        private val prefsRepository: SharedPrefsRepository
    ) : BaseRepository(), QueueRepository {

        override suspend fun createQueue(
            name: String,
            description: String,
            capacity: Int,
            business_associated: String
        ): Either<Failure, String> {

            val queueMap =
                QueueServerModel(name, description, capacity, business_associated).createMap()

            return request(
                apiService.postQueue(
                    prefsRepository.getUserToken().toString(),
                    Gson().toJson(queueMap)
                ), {
                    it
                },
                String.empty()
            )
        }

        override suspend fun fetchQueueById(idQueue: Int): Either<Failure, Queue> {
            return request(apiService.getQueueByQueueId(idQueue), {
                queueAdapter.jsonStringToQueue(it)
            }, String.empty())
        }


        override suspend fun joinQueue(
            idQueue: String
        ): Either<Failure, Queue> {
            val params = HashMap<String, String>()
            params["idQueue"] = idQueue

            return request(apiService.postJoinQueue(params.toString()), {
                queueAdapter.jsonStringToQueue(it)
            }, String.empty())
        }

        override suspend fun fetchQueuesByUser(expand: String?, locked: Boolean?): Either<Failure, List<Queue>> {
            return request(
                apiService.getQueuesByUser(
                    prefsRepository.getUserToken().toString(),
                    expand,
                    locked
                ), {
                    queueAdapter.jsonStringToQueueList(it)
                }, String.empty()
            )
        }

        /*override suspend fun fetchAdminQueuesRepository(isActive: Boolean): Either<Failure, List<Queue>> {
            //TODO Añadir "expand", "locked"

            return request(apiService.getQueues(), {
                queueAdapter.jsonStringToQueueList(it)
            }, String.empty())
        }*/
    }
}


