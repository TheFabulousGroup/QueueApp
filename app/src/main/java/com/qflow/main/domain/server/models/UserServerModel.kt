package com.qflow.main.domain.server.models

/**
 * This will represent the User however we have in firebase
 * */
class UserServerModel{


    //Logica de añadir a firebase
    fun createUser{}
    val userFirebase: Map<String, Any> = HashMap()

    userFirebase.put("email",email)
    userFirebase.put("is_admin", false)
    userFirebase.put("name_lastname", nameLastName)
    userFirebase.put("password",selectedPass)
    userFirebase.put("profile_picture","")
    userFirebase.put("username", username)
}