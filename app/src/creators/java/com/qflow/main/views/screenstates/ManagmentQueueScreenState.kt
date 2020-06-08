package com.qflow.main.views.screenstates

import com.qflow.main.domain.local.models.Queue

sealed class ManagmentQueueScreenState {
    class AdvancedOptions(val queue: Queue):ManagmentQueueScreenState()
    class ClosedQueue(val queue: Queue):ManagmentQueueScreenState()
    class StopResumeQueue(val queue: Queue):ManagmentQueueScreenState()
}