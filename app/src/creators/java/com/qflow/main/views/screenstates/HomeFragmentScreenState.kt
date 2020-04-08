package com.qflow.main.views.screenstates

import com.qflow.main.domain.local.models.Queue

sealed class HomeFragmentScreenState {
    class AccessHome(val id: String): HomeFragmentScreenState()

    class QueuesObtained(val queues: List<Queue>): HomeFragmentScreenState()
}