package com.qflow.main.domain.adapters

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.qflow.main.core.Failure
import com.qflow.main.domain.local.models.Queue
import com.qflow.main.usecases.Either

/**
 * This will be used in the future to parse the firebase repository with the model we use here
 * */
object QueueAdapter {

    fun jsonStringToQueue(jsonQueueString: String): Queue {
        val gsonBuilder =  GsonBuilder()
        gsonBuilder.setDateFormat("yyyy-MM-dd hh:mm:ss")  //TODO check server format
        val gson = gsonBuilder.create()

        val myType = object : TypeToken<Queue>() {}.type
        return gson.fromJson(jsonQueueString, myType)

        /*
        val queueJsonObject = JSONObject(jsonQueueString)   //JSON.parse usado comunmente

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
            if (queueJsonObject.has("businessAssociated")) queueJsonObject.getString("businessAssociated")
            else ""
        val isLock: Boolean =
            if (queueJsonObject.has("isLock")) queueJsonObject.getBoolean("isLock")
            else false
        val id: Int =
            if (queueJsonObject.has("id")) queueJsonObject.getInt("id")
            else -1
        val dateCreated: String? =
            if (queueJsonObject.has("dateCreated")) queueJsonObject.getString("dateCreated")
            else null
        val dateFinished: String? =
            if (queueJsonObject.has("dateFinished")) queueJsonObject.getString("dateFinished")
            else null
        val joinId: Int =
            if (queueJsonObject.has("joinId")) queueJsonObject.getInt("joinId")
            else -1
        val currentPos: Int =
            if (queueJsonObject.has("currentPos")) queueJsonObject.getInt("currentPos")
            else -1

        val formattedDateCreated = dateCreated?.let { Timestamp.valueOf(it) }
        val formattedDateFinished = dateFinished?.let { Timestamp.valueOf(it) }

        return Queue(
            id, name, description, joinId, formattedDateCreated, formattedDateFinished, capacity,
            currentPos, isLock, businessAssociated
        )*/
    }

    fun jsonStringToQueueList(jsonQueueList: String): List<Queue> {

        val gsonBuilder =  GsonBuilder()
        gsonBuilder.setDateFormat("yyyy-MM-dd hh:mm:ss")  //TODO check server format
        val gson = gsonBuilder.create()

        val myType = object : TypeToken<List<Queue>>() {}.type

        return gson.fromJson(jsonQueueList, myType)
        //val mapper = ObjectMapper()       //Jackson option
        //resultList = mapper.readValue(jsonQueueList, object : TypeReference<List<Queue?>?>() {})
    }
}