package com.qflow.main.views.screenstates

sealed class JoinQueueScreenStates {
    class QueueLoaded(val id: Int): JoinQueueScreenStates()
}