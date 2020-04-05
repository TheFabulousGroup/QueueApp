package com.qflow.main.repository

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.qflow.main.core.BaseRepository
import com.qflow.main.core.Failure
import com.qflow.main.domain.*

import com.qflow.main.domain.adapters.QueueAdapter
import com.qflow.main.domain.local.database.AppDatabase
import com.qflow.main.domain.server.models.QueueServerModel
import com.qflow.main.domain.server.models.QueueUserServerModel
import com.qflow.main.usecases.Either

interface QueueRepository {

    fun createQueue(
        name: String,
        description: String,
        capacity: String,
        business_associated: String
    ): Either<Failure, String>

    fun joinQueue(
        id_queue: String,
        id_name: String
    ): Either<Failure, String>

    class General
    constructor(
        private val appDatabase: AppDatabase,       //todo Add local DB for queue ?
        private val queueAdapter: QueueAdapter,
        private val firebasedb: FirebaseFirestore,
        private val firebaseAuth: FirebaseAuth
    ) : BaseRepository(), QueueRepository {

        @RequiresApi(Build.VERSION_CODES.O)
        override fun createQueue(
            name: String,
            description: String,
            capacity: String,
            business_associated: String
            /*date_created: String,
            date_finished: String,
            is_locked: Boolean*/
        ): Either<Failure, String> {

            val queueMap =
                QueueServerModel(
                    name,
                    description,
                    capacity.toInt(),
                    business_associated
                ).createMap()
            //Storing into Firestore
            val taskFirebase = firebasedb.collection(COLLECTION_QUEUE)
                .add(queueMap)


            val queueaux =
                QueueServerModel(name, description, capacity.toInt(), business_associated)
            val t = firebasedb.collection(COLLECTION_QUEUE).document().set(queueaux)
            //TODO cambiar los mapas, quitar los Listener y dejar lo de abajo
            //TODO repetir quitar mapas en userRepo
            t.isSuccessful
            firebasedb.collection(COLLECTION_QUEUE).document().set(queueaux).addOnSuccessListener {

                Log.d(
                    ContentValues.TAG,
                    "DocumentSnapshot added with ID: " + taskFirebase.result?.id
                )
                //Para que devolver algo aqui, ¿solo devolver excepcion si es incorrecto?
                //Either.Right()
            }.addOnFailureListener {
                Log.w(
                    ContentValues.TAG,
                    "Error adding document"
                )
                Either.Left(Failure.NetworkConnection)
            }

            /*
            return if (taskFirebase.isSuccessful) {
                val idFire = taskFirebase.result?.id
                if (idFire == null)
                    Either.Left(Failure.NetworkConnection)
                else {
                    Log.d(
                        ContentValues.TAG,
                        "DocumentSnapshot added with ID: " + taskFirebase.result?.id
                    )
                    /*val localUser =
                        taskFirebase.result?.id?.let {
                            UserDB(
                                id_firebase = it,
                                username = username
                            )
                        }
                    if (localUser != null) {
                        appDatabase.userDatabaseDao.insert(localUser)
                    }*/
                    Either.Right(idFire)
                }
            } else {
                Log.w(
                    ContentValues.TAG,
                    "Error adding document"
                )
                Either.Left(Failure.NetworkConnection)
            }*/
        }

        override fun joinQueue(
            id_queue: String,
            id_user: String
        ): Either<Failure, String> {    //Todo joinQueue
            //val task = firebaseAuth.signInWithEmailAndPassword(email, pass)
            //We need: is_active, is_admin, actualdate, capacity
            val docRef = firebasedb.collection(COLLECTION_QUEUE).document(id_queue)
            docRef.get()
                .addOnSuccessListener { document ->
                    val capa = document.toObject(QueueServerModel::class.java)
                    //capa?.apply {capacity}
                    firebasedb.collection(COLLECTION_QUEUE_USER).whereEqualTo(
                        "id_queue",
                        id_queue
                    ).get().addOnSuccessListener {
                        it.size()
                        var fin = 0
                        capa?.apply {
                            fin = capacity - it.size()
                        }
                        if (fin == 0) {
                            //FAIL
                            Log.d(TAG, "Full capacity")
                        } else {
                            //TODO add user
                            val queueaux = QueueUserServerModel(id_queue, id_user, true, false)
                            firebasedb.collection(COLLECTION_QUEUE).document().set(queueaux)
                                .addOnSuccessListener {
                                    val idFire = taskFirebase.result?.id
                                    if (idFire == null)
                                        Either.Left(Failure.NetworkConnection)
                                    else {
                                        Log.d(
                                            ContentValues.TAG,
                                            "DocumentSnapshot added with ID: " + taskFirebase.result?.id
                                        )

                                        Either.Right(idFire)
                                    }
                                }.addOnFailureListener {
                                    Log.w(
                                        ContentValues.TAG,
                                        "Error adding document"
                                    )
                                    Either.Left(Failure.NetworkConnection)
                                }
                        }
                    }

                    if (document != null) {
                        Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                    } else {
                        Log.d(TAG, "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "get failed with ", exception)
                }


            return if (task.isSuccessful) {
                Log.d(TAG, "You´ve joined")
                Either.Right(firebaseAuth.currentUser!!.uid)

            } else {
                Log.w(TAG, "createUserWithEmail:failure", task.exception)
                Either.Left(Failure.NetworkConnection)
            }

        }
    }

}