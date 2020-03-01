package com.qflow.main.domain.adapters


/**
 * This will be used in the future to parse the firebase repository with the model we use here
 * Adapts a normal user to UserServerModel
 * */
object UserAdapter {
    operator fun invoke(username: String, selectedPass: String, email: String, nameLastName: String): Any {
        Map<String, Object> user = new HashMap<>();
        user.put("first", "Alan");
        user.put("middle", "Mathison");
        user.put("last", "Turing");
        user.put("born", 1912);


        return UserAdapter(username, selectedPass, email, nameLastName)
    }

    val username = ""
    val selectedPass = ""
    val email = ""
    val nameLastName = ""
}