package com.qflow.main.domain.local.models

import com.google.firebase.database.IgnoreExtraProperties
import java.sql.Timestamp
import java.util.*

/**
 * Here we will define the User class for our App
 * */
@IgnoreExtraProperties
data class Queue(
    val id: Int? = -1,
    val name: String? = null,
    val description: String? = null,
    val joinId: Int? = null,
    val dateCreated: Timestamp? = null,
    val dateFinished: Timestamp? = null,
    val capacity: Int? = null,
    val currentPos: Int? = null,
    val isLock: Boolean = false,
    val businessAssociated: String? = null
)
