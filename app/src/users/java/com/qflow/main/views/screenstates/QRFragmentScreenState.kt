package com.qflow.main.views.screenstates

import com.qflow.main.domain.local.models.Queue

sealed class QRFragmentScreenState {
    class QueueLoaded(queue: Queue): QRFragmentScreenState()
}