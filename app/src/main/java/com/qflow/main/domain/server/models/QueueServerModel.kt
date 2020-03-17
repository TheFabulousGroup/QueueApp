package com.qflow.main.domain.server.models

class QueueServerModel(
    val name: String,
    val description: String,
    val capacity: Integer,
    val business_associated: String,
    val date_created: String,
    val date_finished: String = "",
    val is_locked: Boolean = false
) {

    fun createMap(): Map<String, Any> {
        val queueFirebase = HashMap<String, Any>()

        queueFirebase["name"] = this.name
        queueFirebase["description"] = this.description
        queueFirebase["capacity"] = this.capacity
        queueFirebase["business_associated"] = this.business_associated
        queueFirebase["date_created"] = this.date_created
        queueFirebase["date_finished"] = this.date_finished
        queueFirebase["is_locked"] = this.is_locked

        return queueFirebase;
    }

}