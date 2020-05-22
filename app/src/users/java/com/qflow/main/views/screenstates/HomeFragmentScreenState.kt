package com.qflow.main.views.screenstates

import com.qflow.main.domain.local.models.Queue

sealed class HomeFragmentScreenState {
    object JoinedQueue : HomeFragmentScreenState()

    class AccessProfile(val id: String): HomeFragmentScreenState()
    class QueueLoaded(val queue: Queue) : HomeFragmentScreenState()
}