package com.qflow.main.views.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.qflow.main.core.BaseViewModel
import com.qflow.main.core.ScreenState
import com.qflow.main.domain.local.database.AppDatabase
import com.qflow.main.domain.local.models.Queue
import com.qflow.main.domain.server.models.QueueServerModel
import com.qflow.main.usecases.queue.FetchQueueByJoinID
import com.qflow.main.usecases.queue.JoinQueue
import com.qflow.main.views.screenstates.HomeFragmentScreenState
import com.qflow.main.views.screenstates.QRFragmentScreenState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.koin.core.KoinComponent


/**
 * Old ViewModel for the profileFragment
 * */
class HomeViewModel(
    private val findQueueByJoinID: FetchQueueByJoinID,
    private val joinQueue: JoinQueue
): BaseViewModel(), KoinComponent {

    private lateinit var info: QueueServerModel
    private val _screenState: MutableLiveData<ScreenState<HomeFragmentScreenState>> =
        MutableLiveData()
    val screenState: LiveData<ScreenState<HomeFragmentScreenState>>
        get() = _screenState

    private var job = Job()
    private var coroutineScope = CoroutineScope(Dispatchers.Default + job)

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    fun loadQueueToJoin(get: Int) {

        _screenState.value = ScreenState.Loading
        findQueueByJoinID.execute(
            { it.either(::handleFailure, ::handleQueuesObtained) },
            FetchQueueByJoinID.Params(get.toInt()), this.coroutineScope
        )

    }

    private fun handleQueuesObtained(queue: Queue) {
        _screenState.value = ScreenState.Render(HomeFragmentScreenState.QueueLoaded(queue))
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
        _screenState.value = ScreenState.Render(HomeFragmentScreenState.JoinedQueue)
    }
}
