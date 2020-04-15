package com.qflow.main.views.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.qflow.main.core.BaseViewModel
import com.qflow.main.core.ScreenState
import com.qflow.main.usecases.queue.JoinQueue
import com.qflow.main.views.screenstates.JoinQueueScreenStates
import com.qflow.main.views.screenstates.LoginFragmentScreenState
import org.koin.core.KoinComponent

class JoinQueueViewModel(joinQueue: JoinQueue): BaseViewModel(), KoinComponent {
    private val _screenState: MutableLiveData<ScreenState<JoinQueueScreenStates>> =
        MutableLiveData()
    val screenState: LiveData<ScreenState<JoinQueueScreenStates>>
        get() = _screenState

    fun joinQueue(idQueue:String){

    }
}