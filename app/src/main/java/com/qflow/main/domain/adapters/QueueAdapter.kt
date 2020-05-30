package com.qflow.main.domain.adapters

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.qflow.main.core.Failure
import com.qflow.main.domain.local.models.Queue
import com.qflow.main.domain.server.models.QueueDTO
import com.qflow.main.domain.server.models.UserDTO
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

    fun jsonStringToQueueDTO(jsonQueueString: String): QueueDTO {
        val gson = Gson()
        val myType = object : TypeToken<QueueDTO>() {}.type
        return gson.fromJson(jsonQueueString, myType)
    }

    fun jsonStringToQueueId(jsonQueueString: String): Int {
        val gson = Gson()
        val myType = object : TypeToken<Int>() {}.type
        return gson.fromJson(jsonQueueString, myType)
    }
}