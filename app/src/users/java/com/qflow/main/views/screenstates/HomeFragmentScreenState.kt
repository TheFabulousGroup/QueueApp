package com.qflow.main.views.screenstates

import com.qflow.main.domain.local.models.Queue

sealed class HomeFragmentScreenState {

    object JoinedToQueue: HomeFragmentScreenState()
    object JoinedQueue: HomeFragmentScreenState()
    class QueuesActiveObtained(val queues: List<Queue>): HomeFragmentScreenState()
    class QueuesHistoricalObtained(val queues: List<Queue>):HomeFragmentScreenState()
    class AccessProfile(val id: String): HomeFragmentScreenState()
    class QueueToJoinLoaded(val queue: Queue, val isAlreadyInQueue: Boolean): HomeFragmentScreenState()
}