package com.qflow.main.views.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.qflow.main.core.BaseViewModel
import com.qflow.main.core.ScreenState
import com.qflow.main.usecases.queue.JoinQueue
import com.qflow.main.usecases.user.LoginCase
import com.qflow.main.views.screenstates.JoinQueueScreenStates
import com.qflow.main.views.screenstates.LoginFragmentScreenState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.koin.core.KoinComponent

class JoinQueueViewModel(private val joinQueueUC: JoinQueue): BaseViewModel(), KoinComponent {
    private val _screenState: MutableLiveData<ScreenState<JoinQueueScreenStates>> =
        MutableLiveData()
    val screenState: LiveData<ScreenState<JoinQueueScreenStates>>
        get() = _screenState

    private var job = Job()
    private var coroutineScope = CoroutineScope(Dispatchers.Default + job)

    fun joinQueue(idQueue:String){
        _screenState.value = ScreenState.Loading
                //Execute add user to database
        joinQueueUC.execute(
            { it.either(::handleFailure, ::handleJoinQueue) },
            JoinQueue.Params(idQueue),
            this.coroutineScope
        )

    }

    private fun handleJoinQueue(id: String) {
        _screenState.value = ScreenState.Render(JoinQueueScreenStates.JoinSuccessful(id))
    }
}