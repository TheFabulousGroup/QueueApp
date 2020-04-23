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
    var is_active: Boolean = false,
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
        queueFirebase["is_locked"] = this.is_active.toString()

        return queueFirebase;
    }

    companion object {

        fun mapToObject(resultMock: String): QueueServerModel {

            val queueJsonObject = JSONObject(resultMock)

            val name: String =
                if (queueJsonObject.has("name")) queueJsonObject.getString("name")
                else ""
            val description: String =
                if (queueJsonObject.has("description")) queueJsonObject.getString("description")
                else ""
            val capacity: Int =
                if (queueJsonObject.has("capacity")) queueJsonObject.getInt("capacity")
                else -1
            val businessAssociated: String =
                if (queueJsonObject.has("business_associated")) queueJsonObject.getString("business_associated")
                else ""
            val isActive: Boolean =
                if (queueJsonObject.has("is_locked")) queueJsonObject.getBoolean("is_locked")
                else false
            val id: String =
                if (queueJsonObject.has("id")) queueJsonObject.getString("id")
                else ""
            val dateCreated: String? =
                if (queueJsonObject.has("date_created")) queueJsonObject.getString("date_created")
                else null

            val dateFinished: String? =
                if (queueJsonObject.has("date_finished")) queueJsonObject.getString("date_finished")
                else null

            val formattedDateCreated = dateCreated?.let { Date.valueOf(it) }
            val formattedDateFinished = dateFinished?.let { Date.valueOf(it) }

            return QueueServerModel(
                name, description, capacity, businessAssociated,
                formattedDateCreated, formattedDateFinished, isActive, id
            )
//            var name: String,
//            var description: String?,
//            var capacity: Int,
//            var business_associated: String,
//            var date_created: java.util.Date? = null,
//            var date_finished: java.util.Date? = null,
//            var is_active: Boolean = false,
//            val id: String = String.empty()
//            if(queueJsonObject["date_created"] == "" && queueJsonObject["date_created"] == ""){
//                result = QueueServerModel(queueJsonObject["name"] as String, queueJsonObject["description"] as String,
//                    queueJsonObject["capacity"] as Int, queueJsonObject["business_associated"] as String,
//                    null, null, queueJsonObject["is_active"] as Boolean)
//            }else if(queueJsonObject["date_created"] == ""){
//                val formattedDateFinished =  Date.valueOf(queueJsonObject["date_finished"] as String?)
//
//                result = QueueServerModel(queueJsonObject["name"] as String, queueJsonObject["description"] as String,
//                    queueJsonObject["capacity"] as Int, queueJsonObject["business_associated"] as String,
//                    null, formattedDateFinished, queueJsonObject["is_active"] as Boolean)
//            }else if(queueJsonObject["date_finished"] == ""){
//                val formattedDateCreated =  Date.valueOf(queueJsonObject["date_created"] as String?)
//
//                result = QueueServerModel(queueJsonObject["name"] as String, queueJsonObject["description"] as String,
//                    queueJsonObject["capacity"] as Int, queueJsonObject["business_associated"] as String,
//                    formattedDateCreated, null, queueJsonObject["is_active"] as Boolean)
//            }else{
//                val formattedDateCreated =  Date.valueOf(queueJsonObject["date_created"] as String?)
//                val formattedDateFinished =  Date.valueOf(queueJsonObject["date_finished"] as String?)
//
//                result = QueueServerModel(queueJsonObject["name"] as String, queueJsonObject["description"] as String,
//                    queueJsonObject["capacity"] as Int, queueJsonObject["business_associated"] as String,
//                    formattedDateCreated, formattedDateFinished, queueJsonObject["is_active"] as Boolean,
//                    id
//                )
//            }
//
//            return result
        }

        fun mapListToObjectList(resultMock: String): List<QueueServerModel> {
            val jsonArray = JSONArray(resultMock)
            val resList = ArrayList<QueueServerModel>()
            for (i in 0 until jsonArray.length())
                resList.add(mapToObject(jsonArray.get(i).toString()))
            return resList.toList()
        }
    }
}