package com.qflow.main.views.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.qflow.main.core.BaseViewModel
import com.qflow.main.core.ScreenState
import com.qflow.main.domain.local.models.Queue
import com.qflow.main.usecases.queue.FetchQueueByJoinID
import com.qflow.main.usecases.queue.JoinQueue
import com.qflow.main.views.screenstates.QRFragmentScreenState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.koin.core.KoinComponent

class QRFragmentViewModel(
    private val findQueueByJoinID: FetchQueueByJoinID,
    private val joinQueue: JoinQueue
) : BaseViewModel(), KoinComponent {

    private val _screenState: MutableLiveData<ScreenState<QRFragmentScreenState>> =
        MutableLiveData()
    val screenState: LiveData<ScreenState<QRFragmentScreenState>>
        get() = _screenState

    private var job = Job()
    private var coroutineScope = CoroutineScope(Dispatchers.Default + job)

    fun loadQueueToJoin(get: Int) {

        _screenState.value = ScreenState.Loading
        findQueueByJoinID.execute(
            { it.either(::handleFailure, ::handleQueuesObtained) },
            FetchQueueByJoinID.Params(get), this.coroutineScope
        )

    }

    private fun handleQueuesObtained(returnParams: FetchQueueByJoinID.ReturnParams) {
        _screenState.value = ScreenState.Render(
            QRFragmentScreenState.QueueToJoinLoaded(
                returnParams.queue,
                returnParams.alreadyInQueue
            )
        )
    }

    fun joinToQueue(id: Int?) {
        _screenState.value = ScreenState.Loading
        id?.let { JoinQueue.Params(it) }?.let { it ->
            joinQueue.execute (
                { it.either(::handleFailure, ::handleJoinCompleted) },
                it, this.coroutineScope
            )
        }
    }

    private fun handleJoinCompleted(i: Int) {
        _screenState.value = ScreenState.Render(QRFragmentScreenState.JoinedQueue)
    }
}