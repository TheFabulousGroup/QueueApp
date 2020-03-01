package com.qflow.main.domain.server.models

/**
 * This will represent the User however we have in firebase
 * Implements Firebase user logic
 * */


// TODO Entran datos en el constructor y devolvemos un Map
class UserServerModel{

    //values from users collection in Firestore
    val email = ""
    val isAdmin = false
    val nameLastName = ""
    val password = ""
    val profilePicture = ""
    val username = ""


     // En repo comprobamos datos de entrada, si tbien llamamos a adapter, que devuelve
        // la clase UserSserverModel, y metemos los atributos de esta en firesotore, con lo de abajo
    //Logica de añadir a firebase  PASAR A REPO
   /* fun createUser{}
    val userFirebase: Map<String, Any> = HashMap()

    userFirebase.put("email",email)
    userFirebase.put("is_admin", false)
    userFirebase.put("name_lastname", nameLastName)
    userFirebase.put("password",selectedPass)
    userFirebase.put("profile_picture","")
    userFirebase.put("username", username)*/
}