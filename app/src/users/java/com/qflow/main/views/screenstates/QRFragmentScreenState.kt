package com.qflow.main.views.screenstates

import com.qflow.main.domain.local.models.Queue

sealed class QRFragmentScreenState {
    object JoinedQueue : QRFragmentScreenState()
    class QueueToJoinLoaded(val queue: Queue, val isAlreadyInQueue: Boolean): QRFragmentScreenState()
}