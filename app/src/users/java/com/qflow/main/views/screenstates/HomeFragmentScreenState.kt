package com.qflow.main.views.screenstates

import com.qflow.main.domain.local.models.Queue

sealed class HomeFragmentScreenState {

    class JoinedToQueue: HomeFragmentScreenState()
    class JoinedQueue: HomeFragmentScreenState()
    class QueuesActiveObtained(val queues: List<Queue>): HomeFragmentScreenState()
    class QueuesHistoricalObtained(val queues: List<Queue>):HomeFragmentScreenState()
    class AccessProfile(val id: String): HomeFragmentScreenState()
    class QueueLoaded(val queue: Queue): HomeFragmentScreenState()
}