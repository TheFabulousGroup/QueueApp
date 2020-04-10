package com.qflow.main.domain.server.models

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class QueueUserServerModel(
    val id_queue: String,
    val id_user: String,
    val is_active: Boolean,
    val is_admin: Boolean
)