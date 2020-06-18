package com.qflow.main.views.viewmodels

import android.app.Person
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.qflow.main.core.BaseViewModel
import com.qflow.main.core.ScreenState
import com.qflow.main.core.ScreenState.Loading
import com.qflow.main.domain.local.database.user.UserDB
import com.qflow.main.domain.local.models.Queue
import com.qflow.main.usecases.queue.*
import com.qflow.main.views.screenstates.HomeFragmentScreenState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.koin.core.KoinComponent
import java.sql.Timestamp

class HomeViewModel(
    private val fetchQueuesByUser: FetchQueuesByUser,
    private val advance: AdvancedQueueById,
    private val close: CloseQueueById,
    private val stop: StopQueueById,
    private val resume: ResumeQueueById
) : BaseViewModel(), KoinComponent {

    private val _screenState: MutableLiveData<ScreenState<HomeFragmentScreenState>> =
        MutableLiveData()
    val screenState: LiveData<ScreenState<HomeFragmentScreenState>>
        get() = _screenState

    private var job = Job()
    private var coroutineScope = CoroutineScope(Dispatchers.Default + job)

    /*enable queues*/
    fun getQueues(expand: String?, finished: Boolean?) {
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

    /*disable queues*/
    fun getHistory(expand: String?, finished: Boolean?) {
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


    fun advanceQueue(
        idQueue: Int,
        isLock: Boolean,
        date: Timestamp,
        numPerson: Int,
        capacity: Int
    ) {
        advance.execute(
            { it.either(::handleFailure, ::handleManageQueue) },
            AdvancedQueueById.Params(idQueue, isLock, date, numPerson, capacity),
            this.coroutineScope
        )
    }

    private fun handleManageQueue(queue: Queue) {
        this._screenState.value =
            ScreenState.Render(HomeFragmentScreenState.QueueManageDialog(queue))
    }

    fun stopQueue(idQueue: Int, numPerson:Int) {
        stop.execute(
            { it.either(::handleFailure, ::handleManageQueue) },
            StopQueueById.Params(idQueue,numPerson),
            this.coroutineScope
        )
    }

    fun resumeQueue(idQueue: Int) {
        resume.execute(
            { it.either(::handleFailure, ::handleManageQueue) },
            ResumeQueueById.Params(idQueue),
            this.coroutineScope
        )
    }

    fun closeQueue(idQueue: Int,numPerson:Int,dateFinish:Timestamp) {
        close.execute(
            { it.either(::handleFailure, ::handleManageQueue) },
            CloseQueueById.Params(idQueue,numPerson,dateFinish),
            this.coroutineScope
        )
    }


}
