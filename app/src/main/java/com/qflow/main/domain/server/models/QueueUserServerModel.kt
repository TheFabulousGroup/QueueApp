package com.qflow.main.domain.server.models

class QueueUserServerModel(
    val id_queue: String,
    val id_user: String,
    val is_active: Boolean,
    val is_admin: Boolean
)