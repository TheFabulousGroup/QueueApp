package com.qflow.main.views.screenstates

import com.qflow.main.domain.local.models.Queue

sealed class InfoQueueScreenState {
    class QueueObtained(val queue: Queue): InfoQueueScreenState()

}