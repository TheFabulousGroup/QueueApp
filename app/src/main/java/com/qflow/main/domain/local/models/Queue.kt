package com.qflow.main.domain.local.models

import java.sql.Timestamp

class Queue (
    val id: String,
    val name: String,
    val description: String?,
    val capacity: Int,
    val business_associated: String,
    val date_created: Timestamp? = null,
    val date_finised: Timestamp? = null,
    val is_locked: Boolean = false
)