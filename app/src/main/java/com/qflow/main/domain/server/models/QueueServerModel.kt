package com.qflow.main.domain.server.models

import org.json.JSONArray
import java.sql.Timestamp

class QueueServerModel(
    val name: String,
    val description: String?,
    val capacity: Int,
    val business_associated: String,
    val date_created: Timestamp? = null,
    val date_finished: Timestamp? = null,
    val is_locked: Boolean = false
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
            TODO()
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