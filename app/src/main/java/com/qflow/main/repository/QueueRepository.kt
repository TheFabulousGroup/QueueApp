package com.qflow.main.repository

import android.content.ContentValues
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.qflow.main.core.BaseRepository
import com.qflow.main.core.Failure
import com.qflow.main.domain.adapters.QueueAdapter
import com.qflow.main.domain.local.database.AppDatabase
import com.qflow.main.domain.local.models.Queue
import com.qflow.main.domain.server.models.QueueServerModel
import com.qflow.main.usecases.Either

interface QueueRepository {

    fun createQueue(
        name: String,
        description: String,
        capacity: String,
        business_associated: String
    ): Either<Failure, String>

    fun fetchAdminQueue(   //WIP: Se podra añadir booleano para si es busqueda de admin o no
        id_user: String
    ): Either<Failure, ArrayList<Queue>>

    class General
    constructor(
        private val appDatabase: AppDatabase,       //todo Add local DB for queue ?
        private val queueAdapter: QueueAdapter,
        private val firebasedb: FirebaseFirestore,
        private val firebaseAuth: FirebaseAuth
    ) : BaseRepository(), QueueRepository {

        @RequiresApi(Build.VERSION_CODES.O)     //TODO borrá o actualisá
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
                QueueServerModel(name, description, capacity.toInt(), business_associated).createMap()
            //Storing into Firestore
            val taskFirebase = firebasedb.collection("queue")
                .add(queueMap)

            return if(taskFirebase.isSuccessful){
                val idFire = taskFirebase.result?.id
                if(idFire == null)
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
            }
            else{
                Log.w(
                    ContentValues.TAG,
                    "Error adding document"
                )
                Either.Left(Failure.NetworkConnection)
            }
        }

        override fun fetchAdminQueue(id_user: String): Either<Failure, ArrayList<Queue>> {
            //TODO firebase functions will return queues in an array
            //Devolvemos un array de array de strings dummie(colecciones queue del user pasado)

            //Definir adaptador, de functions se devuelve un array de QueueServelModel
            //Nos creamos dummies de QueueServerModel que llegan de FB
                //Devolvemos QueueAdapter

            var queuesArray = ArrayList<Queue>()

            for(i in 0..7){
                var queueServerModel = QueueServerModel("Cola num $i", "Bonita descripc",
                    100, "BsnssDisney")

                queuesArray[i] = QueueAdapter.adapt(queueServerModel)
            }

            return Either.Right(queuesArray)
        }

        /*override fun signIn(email: String,pass: String): Either<Failure, String> {    //Todo joinQueue
            val task = firebaseAuth.signInWithEmailAndPassword(email, pass)
            return if (task.isSuccessful) {
                Log.d(TAG, "You´re signin in succesfully ")
                Either.Right(firebaseAuth.currentUser!!.uid)

            } else {
                Log.w(TAG, "createUserWithEmail:failure", task.exception)
                Either.Left(Failure.NetworkConnection)
            }

        }*/
    }

}