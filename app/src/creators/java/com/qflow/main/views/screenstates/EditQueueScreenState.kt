package com.qflow.main.views.screenstates

sealed class EditQueueScreenState {
    class QueueEditedCorrectly(val id: String): EditQueueScreenState()
}