package com.qflow.main.domain.server.models

import android.os.Build
import androidx.annotation.RequiresApi
import org.json.JSONArray
import org.json.JSONObject
import java.sql.Date

class QueueServerModel(
    var name: String,
    var description: String?,
    var capacity: Int,
    var business_associated: String,
    var date_created: java.util.Date? = null,
    var date_finished: java.util.Date? = null,
    var is_locked: Boolean = false
) {
    fun createMap(): Map<String, String> {
        val queueFirebase = HashMap<String, String>()

        val dateC = if (date_created == null)
            com.google.firebase.Timestamp.now().toString()
        else
            this.date_created.toString()
        val dateF = if (date_finished == null)
            "null"
        else
            this.date_finished.toString()

        queueFirebase["name"] = this.name
        queueFirebase["description"] = this.description ?: ""
        queueFirebase["capacity"] = this.capacity.toString()
        queueFirebase["business_associated"] = this.business_associated
        queueFirebase["date_created"] = dateC.toString()
        queueFirebase["date_finished"] = dateF.toString()
        queueFirebase["is_locked"] = this.is_locked.toString()

        return queueFirebase;
    }

    companion object{

        @RequiresApi(Build.VERSION_CODES.O)
        fun mapToObject(resultMock: String): QueueServerModel {

            /*var name: String,
    var description: String?,
    var capacity: Int,
    var business_associated: String,
    var date_created: Timestamp? = null,
    var date_finished: Timestamp? = null,
    var is_locked: Boolean = false*/


            var queueJsonObject = JSONObject(resultMock)
            var result : QueueServerModel

            if(queueJsonObject["date_created"] == "" && queueJsonObject["date_created"] == ""){
                result = QueueServerModel(queueJsonObject["name"] as String, queueJsonObject["description"] as String,
                    queueJsonObject["capacity"] as Int, queueJsonObject["business_associated"] as String,
                    null, null, queueJsonObject["is_locked"] as Boolean)
            }else if(queueJsonObject["date_created"] == ""){
                val formattedDateFinished =  Date.valueOf(queueJsonObject["date_finished"] as String?)

                result = QueueServerModel(queueJsonObject["name"] as String, queueJsonObject["description"] as String,
                    queueJsonObject["capacity"] as Int, queueJsonObject["business_associated"] as String,
                    null, formattedDateFinished, queueJsonObject["is_locked"] as Boolean)
            }else if(queueJsonObject["date_finished"] == ""){
                val formattedDateCreated =  Date.valueOf(queueJsonObject["date_created"] as String?)

                result = QueueServerModel(queueJsonObject["name"] as String, queueJsonObject["description"] as String,
                    queueJsonObject["capacity"] as Int, queueJsonObject["business_associated"] as String,
                    formattedDateCreated, null, queueJsonObject["is_locked"] as Boolean)
            }else{
                val formattedDateCreated =  Date.valueOf(queueJsonObject["date_created"] as String?)
                val formattedDateFinished =  Date.valueOf(queueJsonObject["date_finished"] as String?)

                result = QueueServerModel(queueJsonObject["name"] as String, queueJsonObject["description"] as String,
                    queueJsonObject["capacity"] as Int, queueJsonObject["business_associated"] as String,
                    formattedDateCreated, formattedDateFinished, queueJsonObject["is_locked"] as Boolean)
            }

            return result
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun mapListToObjectList(resultMock: String): List<QueueServerModel> {
            val jsonArray = JSONArray(resultMock)
            val resList = ArrayList<QueueServerModel>()
            for (i in 0 until jsonArray.length()-1)
                resList.add(mapToObject( jsonArray.get(i).toString()))
            return resList.toList()
        }
    }
}