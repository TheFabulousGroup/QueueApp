package com.qflow.main.repository

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

    suspend fun joinQueue(id_queue: String): Either<Failure, Queue>
    suspend fun fetchAdminQueuesRepository(isActive: Boolean): Either<Failure, List<Queue>>
    suspend fun fetchQueueById(id_queue: Int): Either<Failure, Queue>

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
                QueueServerModel(
                    name,
                    description,
                    capacity,
                    business_associated
                ).createMap()

            return request(
                apiService.postQueue(prefsRepository.getUserToken().toString(), Gson().toJson(queueMap)), {
                    it
                },
                String.empty()
            )
        }

        override suspend fun joinQueue(
            id_queue: String
        ): Either<Failure, Queue> {
            val params = HashMap<String, String>()
            params["id_queue"] = id_queue
            return request(apiService.postJoinQueue(params.toString()), { res ->
                val resultMock =
                    "   {\n" +
                            "      \"business_associated\":\"\",\n" +
                            "      \"capacity\":0,\n" +
                            "      \"description\":\"\",\n" +
                            "      \"is_locked\":false,\n" +
                            "      \"name\":\"\"\n" +
                            "   }\n"
                queueAdapter.queueSMToQueue(QueueServerModel.mapToObject(resultMock))
            }, String.empty())
        }

        override suspend fun fetchAdminQueuesRepository(isActive: Boolean): Either<Failure, List<Queue>> {

            val params = HashMap<String, String>()
            params["is_active"] = true.toString()
            return request(apiService.getQueues(), {
                val resultMock = "[\n" +
                        "   {\n" +
                        "      \"id\":\"1\",\n" +
                        "      \"business_associated\":\"\",\n" +
                        "      \"capacity\":0,\n" +
                        "      \"description\":\"\",\n" +
                        "      \"is_active\":false,\n" +
                        "      \"name\":\"ejemplo\"\n" +
                        "   }\n" +
                        "]"
                queueAdapter.queueSMListToQueueList(QueueServerModel.mapListToObjectList(resultMock))
            }, String.empty())
        }

        override suspend fun fetchQueueById(id_queue: Int): Either<Failure, Queue> {

            return request(apiService.getQueue(id_queue), {
                val resultMock =
                    "   {\n" +
                            "      \"id\":\"1\",\n" +
                            "      \"business_associated\":\"Empresa de prueba\",\n" +
                            "      \"capacity\":155,\n" +
                            "      \"description\":\"Descripcion pr\",\n" +
                            "      \"is_active\":false,\n" +
                            "      \"name\":\"Cola de ejemplo\"\n" +
                            "   }\n"

                queueAdapter.queueSMToQueue(QueueServerModel.mapToObject(resultMock))
                //queueAdapter.queueSMListToQueueList(QueueServerModel.mapListToObjectList(resultMock))
            }, String.empty())
        }

    }
}
