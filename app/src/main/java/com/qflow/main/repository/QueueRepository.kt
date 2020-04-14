package com.qflow.main.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.functions.FirebaseFunctions
import com.qflow.main.core.BaseRepository
import com.qflow.main.core.Failure
import com.qflow.main.domain.adapters.QueueAdapter
import com.qflow.main.domain.local.database.AppDatabase
import com.qflow.main.domain.local.models.Queue
import com.qflow.main.domain.server.models.QueueServerModel
import com.qflow.main.usecases.Either
import java.util.ArrayList

interface QueueRepository {

    suspend fun createQueue(
        name: String,
        description: String,
        capacity: Int,
        business_associated: String
    ): Either<Failure, String>
    suspend fun joinQueue(id_queue: String, id_name: String): Either<Failure, Queue>
    suspend fun fetchAdminActiveQueuesRepository(id_user: String): Either<Failure, List<Queue>>
    suspend fun fetchQueueById(id_queue: String): Either<Failure, Queue>

    class General
    constructor(
        private val appDatabase: AppDatabase,       //todo Add local DB for queue ?
        private val queueAdapter: QueueAdapter,
        private val firebasedb: FirebaseFirestore,
        private val firebaseAuth: FirebaseAuth,
        private val firebaseFunctions: FirebaseFunctions
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

            val taskFunctions = firebaseFunctions.getHttpsCallable("addQueue").call(queueMap)
            return firebaseRequest(taskFunctions) { res ->

                res.data.toString()

            }
        }

        override suspend fun joinQueue(
            id_queue: String,
            id_user: String
        ): Either<Failure, Queue> {
            val params = HashMap<String, String>()
            params["id_queue"] = id_queue
            params["id_user"] = id_user
            val taskFunctions = firebaseFunctions.getHttpsCallable("joinQueue").call(params)
            return firebaseRequest(taskFunctions) { res ->
                val resultMock  =
                        "   {\n" +
                        "      \"business_associated\":\"\",\n" +
                        "      \"capacity\":0,\n" +
                        "      \"date_created\":\"\",\n" +
                        "      \"date_finished\":\"\",\n" +
                        "      \"description\":\"\",\n" +
                        "      \"is_locked\":false,\n" +
                        "      \"name\":\"\"\n" +
                        "   }\n"
                queueAdapter.queueSMToQueue(QueueServerModel.mapToObject(resultMock))
            }
        }

        override suspend fun fetchAdminActiveQueuesRepository(id_user: String): Either<Failure, List<Queue>> {

            val params = HashMap<String, String>()
            params["id_user"] = id_user
            params["is_active"] = true.toString()
            val taskFunctions = firebaseFunctions.getHttpsCallable("fetchQueues").call(params)
            return firebaseRequest(taskFunctions){
                val resultMock  = "[\n" +
                    "   {\n" +
                    "      \"id\":\"1\",\n"+
                    "      \"business_associated\":\"\",\n" +
                    "      \"capacity\":0,\n" +
                    "      \"date_created\":\"\",\n" +
                    "      \"date_finished\":\"\",\n" +
                    "      \"description\":\"\",\n" +
                    "      \"is_locked\":false,\n" +
                    "      \"name\":\"ejemplo\"\n" +
                    "   }\n" +
                    "]"
                queueAdapter.queueSMListToQueueList(QueueServerModel.mapListToObjectList(resultMock))
            }
        }

        override suspend fun fetchQueueById(id_queue: String): Either<Failure, Queue> {

            val params = HashMap<String, String>()
            params["id_queue"] = id_queue
            params["is_active"] = true.toString()
            //TODO add fetchQueueById
            val taskFunctions = firebaseFunctions.getHttpsCallable("fetchQueues").call(params)
            return firebaseRequest(taskFunctions){
                val resultMock  = "[\n" +
                        "   {\n" +
                        "      \"id\":\"1\",\n"+
                        "      \"business_associated\":\"Empresa de prueba\",\n" +
                        "      \"capacity\":0,\n" +
                        "      \"date_created\":\"\",\n" +
                        "      \"date_finished\":\"\",\n" +
                        "      \"description\":\"Descripcion de prueba a mostrar\",\n" +
                        "      \"is_locked\":false,\n" +
                        "      \"name\":\"Cola de ejemplo\"\n" +
                        "   }\n" +
                        "]"
                queueAdapter.queueSMToQueue(QueueServerModel.mapToObject(resultMock))
                //queueAdapter.queueSMListToQueueList(QueueServerModel.mapListToObjectList(resultMock))
            }
        }

//            //val task = firebaseAuth.signInWithEmailAndPassword(email, pass)
//            //We need: is_active, is_admin, actualdate, capacity
//            val docRef = firebasedb.collection(COLLECTION_QUEUE).document(id_queue)
//            docRef.get()
//                .addOnSuccessListener { document ->
//                    val capa = document.toObject(QueueServerModel::class.java)
//                    //capa?.apply {capacity}
//                    firebasedb.collection(COLLECTION_QUEUE_USER).whereEqualTo(
//                        "id_queue",
//                        id_queue
//                    ).get().addOnSuccessListener {
//                        it.size()
//                        var fin = 0
//                        capa?.apply {
//                            fin = capacity - it.size()
//                        }
//                        if (fin == 0) {
//                            //FAIL
//                            Log.d(TAG, "Full capacity")
//                        } else {
//                            //TODO add user
//                            val queueaux = QueueUserServerModel(id_queue, id_user, true, false)
//                            firebasedb.collection(COLLECTION_QUEUE).document().set(queueaux)
//                                .addOnSuccessListener {
//                                    val idFire = taskFirebase.result?.id
//                                    if (idFire == null)
//                                        Either.Left(Failure.NetworkConnection)
//                                    else {
//                                        Log.d(
//                                            ContentValues.TAG,
//                                            "DocumentSnapshot added with ID: " + taskFirebase.result?.id
//                                        )
//
//                                        Either.Right(idFire)
//                                    }
//                                }.addOnFailureListener {
//                                    Log.w(
//                                        ContentValues.TAG,
//                                        "Error adding document"
//                                    )
//                                    Either.Left(Failure.NetworkConnection)
//                                }
//                        }
//                    }
//
//                    if (document != null) {
//                        Log.d(TAG, "DocumentSnapshot data: ${document.data}")
//                    } else {
//                        Log.d(TAG, "No such document")
//                    }
//                }
//                .addOnFailureListener { exception ->
//                    Log.d(TAG, "get failed with ", exception)
//                }
//
//
//            return if (task.isSuccessful) {
//                Log.d(TAG, "You´ve joined")
//                Either.Right(firebaseAuth.currentUser!!.uid)
//
//            } else {
//                Log.w(TAG, "createUserWithEmail:failure", task.exception)
//                Either.Left(Failure.NetworkConnection)
//            }
//
//        }
    }
}
