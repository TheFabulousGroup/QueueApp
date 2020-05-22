package com.qflow.main.views.screenstates

import com.qflow.main.domain.local.models.Queue

sealed class QRFragmentScreenState {
    object JoinedQueue : QRFragmentScreenState()
    class QueueLoaded(val queue: Queue): QRFragmentScreenState()
}