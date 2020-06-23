package com.qflow.main.views.screenstates

sealed class CreateQueueScreenState {
    class QueueCreatedCorrectly(val id: String): CreateQueueScreenState()
}