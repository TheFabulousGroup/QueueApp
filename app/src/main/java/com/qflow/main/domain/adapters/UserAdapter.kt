package com.qflow.main.domain.adapters

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.qflow.main.domain.server.models.UserDTO


/**
 * This will be used in the future to parse the firebase repository with the model we use here
 * */
object UserAdapter {

    fun jsonStringToUserDTO(jsonUserString: String): UserDTO {
        val gson = Gson()
        val myType = object : TypeToken<UserDTO>() {}.type
        return gson.fromJson(jsonUserString, myType)
    }

}