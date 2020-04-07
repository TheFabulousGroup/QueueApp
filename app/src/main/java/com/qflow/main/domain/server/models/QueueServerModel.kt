package com.qflow.main.domain.server.models

import com.google.gson.JsonParser
import org.json.JSONArray
import org.json.JSONObject
import java.sql.Timestamp

class QueueServerModel(
    var name: String,
    var description: String?,
    var capacity: Int,
    var business_associated: String,
    var date_created: Timestamp? = null,
    var date_finished: Timestamp? = null,
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
        queueFirebase["date_created"] = dateC
        queueFirebase["date_finished"] = dateF
        queueFirebase["is_locked"] = this.is_locked.toString()

        return queueFirebase;
    }

    companion object{

        fun mapToObject(resultMock: String): QueueServerModel {


            /*var name: String,
    var description: String?,
    var capacity: Int,
    var business_associated: String,
    var date_created: Timestamp? = null,
    var date_finished: Timestamp? = null,
    var is_locked: Boolean = false*/

            var queueJsonObject = JSONObject(resultMock)

            var result =  QueueServerModel(queueJsonObject["name"] as String, queueJsonObject["description"] as String,
                queueJsonObject["capacity"] as Int, queueJsonObject["business_associated"] as String,
                queueJsonObject["date_created"] as Timestamp?,
                queueJsonObject["date_finished"] as Timestamp?, queueJsonObject["is_locked"] as Boolean)

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