package com.qflow.main.domain.adapters
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

/**
 * This will be used in the future to parse the firebase repository with the model we use here
 * Adapts a normal user to UserServerModel
 * */
object UserAdapter {
    private lateinit var fdb: FirebaseFirestore
    operator fun invoke(username: String, selectedPass: String, email: String, nameLastName: String): Any {

        val user = HashMap<String,Any>()
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