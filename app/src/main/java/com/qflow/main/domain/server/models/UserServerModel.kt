package com.qflow.main.domain.server.models


/**
 * This will represent the User however we have in firebase
 * Implements Firebase user logic
 * */

class UserServerModel(
    val username: String,
    val password: String,
    val email: String,
    val nameLastname: String,
    val isAdmin: Boolean = false
) {


    fun createMap(): Map<String, Any> {
        val userFirebase = HashMap<String, Any>()

        userFirebase["email"] = this.email
        userFirebase["is_admin"] = this.isAdmin
        userFirebase["name_lastname"] = this.nameLastname
        userFirebase["password"] = this.password
        userFirebase["profile_picture"] = ""
        userFirebase["username"] = this.username
        return userFirebase;
    }

}