package com.qflow.main.views.screenstates

sealed class JoinQueueScreenStates {
    class JoinSuccessful(val idQueue: Int): JoinQueueScreenStates()

}