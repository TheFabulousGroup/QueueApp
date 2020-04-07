package com.qflow.main.domain.server.models

import java.sql.Timestamp

class QueueServerModel(
    val name: String,
    private val description: String?,
    private val capacity: Int,
    private val business_associated: String,
    private val date_created: Timestamp? = null,
    private val date_finished: Timestamp? = null,
    private val is_locked: Boolean = false
) {
    fun createMap(): Map<String, String> {
        val queueFirebase = HashMap<String, String>()

        val dateC = if (date_created == null)
            com.google.firebase.Timestamp.now().toString()
        else
            this.date_created.toString()
        val dateF = if (date_finished == null)
            "null"
        else
            this.date_finished.toString()

        queueFirebase["name"] = this.name
        queueFirebase["description"] = this.description ?: ""
        queueFirebase["capacity"] = this.capacity.toString()
        queueFirebase["business_associated"] = this.business_associated
        queueFirebase["date_created"] = dateC
        queueFirebase["date_finished"] = dateF
        queueFirebase["is_locked"] = this.is_locked.toString()

        return queueFirebase;
    }


}