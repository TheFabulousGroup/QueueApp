package com.qflow.main.domain.server.models

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class QueueServerModel(
    val name: String,
    val description: String,
    val capacity: Int,
    val business_associated: String
) {

    @RequiresApi(Build.VERSION_CODES.O)
    fun createMap(): Map<String, Any> {
        val queueFirebase = HashMap<String, Any>()

        queueFirebase["name"] = this.name
        queueFirebase["description"] = this.description
        queueFirebase["capacity"] = this.capacity
        queueFirebase["business_associated"] = this.business_associated
        queueFirebase["date_created"] = getCurrentDate()
        queueFirebase["date_finished"] = ""
        queueFirebase["is_locked"] = false

        return queueFirebase;
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCurrentDate(): String {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

        return current.format(formatter)
    }

}