package com.qflow.main.views.viewmodels

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.qflow.main.core.BaseViewModel
import com.qflow.main.core.ScreenState
import com.qflow.main.core.ScreenState.Loading
import com.qflow.main.domain.local.SharedPrefsRepository
import com.qflow.main.domain.local.models.Queue
import com.qflow.main.domain.server.models.QueueServerModel
import com.qflow.main.usecases.queue.FetchQueueByJoinID
import com.qflow.main.usecases.queue.FetchQueuesByUser
import com.qflow.main.usecases.queue.JoinQueue
import com.qflow.main.views.screenstates.HomeFragmentScreenState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.koin.core.KoinComponent


/**
 * Old ViewModel for the profileFragment
 * */
class HomeViewModel(
    private val findQueueByJoinID: FetchQueueByJoinID,
    private val joinQueue: JoinQueue,
    private val fetchQueuesByUser: FetchQueuesByUser,
    private val sharedPrefsRepository: SharedPrefsRepository
): BaseViewModel(), KoinComponent {

    val UPDATE_PERCENTAGE_TIME = 60* 1000L

    private lateinit var info: QueueServerModel
    private val _screenState: MutableLiveData<ScreenState<HomeFragmentScreenState>> =
        MutableLiveData()
    val screenState: LiveData<ScreenState<HomeFragmentScreenState>>
        get() = _screenState

    private var job = Job()
    private var coroutineScope = CoroutineScope(Dispatchers.Default + job)

    private val mainHandler = Handler()

    private val timerUpdatePercent = object : Runnable {
        override fun run() {
            getCurrentQueues("user", false)
            getHistoricalQueues("user", true)
            mainHandler.postDelayed(this, UPDATE_PERCENTAGE_TIME)
        }
    }

    init {
        mainHandler.postDelayed(timerUpdatePercent, UPDATE_PERCENTAGE_TIME)
    }


    override fun onCleared() {
        super.onCleared()
        job.cancel()
        mainHandler.removeCallbacks(timerUpdatePercent)
    }

    fun loadQueueToJoin(get: Int) {

        _screenState.value = Loading
        findQueueByJoinID.execute(
            { it.either(::handleFailure, ::handleLoadQueueObtained) },
            FetchQueueByJoinID.Params(get), this.coroutineScope
        )
    }

    private fun handleLoadQueueObtained(returnParams : FetchQueueByJoinID.ReturnParams) {
        _screenState.value = ScreenState.Render(HomeFragmentScreenState.QueueToJoinLoaded(returnParams.queue, returnParams.alreadyInQueue))
    }

    fun joinToQueue(id: Int?) {
        _screenState.value = Loading
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

    fun getCurrentQueues(expand: String?, finished: Boolean?) {
        _screenState.value = Loading
        fetchQueuesByUser.execute(
            { it.either(::handleFailure, ::handleQueuesObtained) },
            FetchQueuesByUser.Params(expand, finished),
            this.coroutineScope
        )
    }

    private fun handleQueuesObtained(queues: List<Queue>) {
        this._screenState.value =
            ScreenState.Render(HomeFragmentScreenState.QueuesActiveObtained(queues))
    }

    fun getHistoricalQueues(expand: String?, finished: Boolean?) {
        _screenState.value = Loading
        fetchQueuesByUser.execute(
            { it.either(::handleFailure, ::handleHistoryQueues) },
            FetchQueuesByUser.Params(expand, finished),
            this.coroutineScope
        )
    }

    private fun handleHistoryQueues(queues: List<Queue>) {
        this._screenState.value =
            ScreenState.Render(HomeFragmentScreenState.QueuesHistoricalObtained(queues))
    }

    fun logout() {
        sharedPrefsRepository.putUserToken(null)
    }
}
