package com.qflow.main.domain.local.models

import com.google.firebase.database.IgnoreExtraProperties

/**
 * Here we will define the User class for our App
 * */
@IgnoreExtraProperties
data class Queue(
    val name: String? = null, val description: String? = null, val capacity: Int? = null,
    val business_associated: String? = null, val id: String? = null
)
