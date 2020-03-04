package com.qflow.main.domain.server.models

/**
 * This will represent the User however we have in firebase
 * */

class UserServerModel (username: String,
                       password: String,
                       email: String,
                       nameLastname: String
){
    var email: String
    var password: String
    var username: String
    var nameLastname: String

    init{
        this.email = email
        this.password = password
        this.username = username
        this.nameLastname = nameLastname
    }

    fun createMap(): Map<String, Any>{
        val userFirebase = HashMap<String, Any>()

        userFirebase.put("email",this.email)
        userFirebase.put("is_admin", false)
        userFirebase.put("name_lastname", this.nameLastname)
        userFirebase.put("password",this.password)
        userFirebase.put("profile_picture","")
        userFirebase.put("username", this.username)

        return userFirebase;
    }

}
