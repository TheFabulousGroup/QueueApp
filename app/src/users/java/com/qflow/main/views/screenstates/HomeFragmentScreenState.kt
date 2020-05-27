package com.qflow.main.views.screenstates

import com.qflow.main.domain.local.models.Queue

sealed class HomeFragmentScreenState {

    object JoinedToQueue : HomeFragmentScreenState()
    class JoinedQueue(val queues: List<Queue>) : HomeFragmentScreenState()
    class QueuesActiveObtained(val queues: List<Queue>): HomeFragmentScreenState()
    class QueuesHistoricalObtained(val queues: List<Queue>):HomeFragmentScreenState()
    class AccessProfile(val id: String): HomeFragmentScreenState()
    class QueueLoaded(val queue: Queue) : HomeFragmentScreenState()
}