package com.qflow.main.domain.server.models

import com.qflow.main.core.extensions.empty
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
    var is_locked: Boolean = false,
    val id: String = String.empty()
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

        queueFirebase["id"] = this.id
        queueFirebase["name"] = this.name
        queueFirebase["description"] = this.description ?: ""
        queueFirebase["capacity"] = this.capacity.toString()
        queueFirebase["business_associated"] = this.business_associated
        queueFirebase["date_created"] = dateC
        queueFirebase["date_finished"] = dateF
        queueFirebase["is_locked"] = this.is_locked.toString()

        return queueFirebase;
    }

    companion object{

        fun mapToObject(resultMock: String): QueueServerModel {

            val queueJsonObject = JSONObject(resultMock)
            val result : QueueServerModel

            val id: String =
                if(queueJsonObject.has("id")) queueJsonObject.getString("id")
                else ""


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
                    formattedDateCreated, formattedDateFinished, queueJsonObject["is_locked"] as Boolean,
                    id
                )
            }

            return result
        }

        fun mapListToObjectList(resultMock: String): List<QueueServerModel> {
            val jsonArray = JSONArray(resultMock)
            val resList = ArrayList<QueueServerModel>()
            for (i in 0 until jsonArray.length())
                resList.add(mapToObject( jsonArray.get(i).toString()))
            return resList.toList()
        }
    }
}