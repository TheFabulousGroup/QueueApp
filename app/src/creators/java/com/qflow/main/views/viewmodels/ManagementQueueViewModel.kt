package com.qflow.main.views.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.qflow.main.core.BaseViewModel
import com.qflow.main.core.ScreenState
import com.qflow.main.domain.local.models.Queue
import com.qflow.main.usecases.queue.*
import com.qflow.main.views.screenstates.ManagmentQueueScreenState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.koin.core.KoinComponent

class ManagementQueueViewModel(
    //private val advance:AdvancedQueueBy,
    private val close: CloseQueueById,
    private val stop: StopQueueById,
    private val resume: ResumeQueueById
) : BaseViewModel(), KoinComponent {

    private val _screenState: MutableLiveData<ScreenState<ManagmentQueueScreenState>> =
        MutableLiveData()
    val screenState: LiveData<ScreenState<ManagmentQueueScreenState>>
        get() = _screenState

    private var job = Job()
    private var coroutineScope = CoroutineScope(Dispatchers.Default + job)


    //TODO
    /*fun advanceQueue(){

     }
     private fun handleAdvanceQueue(queue: Queue) {
             this._screenState.value = ScreenState.Render(ManagmentQueueScreenState.AdvancedOptions(queue))
     }*/
    fun stopQueue(idQueue: Int) {
        stop.execute(
            { it.either(::handleFailure, ::handleStopQueue) },
            StopQueueById.Params(idQueue),
            this.coroutineScope
        )
    }

    private fun handleStopQueue(queue: Queue) {

    }

    fun resumeQueue(idQueue: Int) {
        resume.execute(
            { it.either(::handleFailure, ::handleResumeQueue) },
            ResumeQueueById.Params(idQueue),
            this.coroutineScope
        )
    }

    private fun handleResumeQueue(queue: Queue) {

    }

    fun closeQueue(idQueue: Int) {
        close.execute(
            { it.either(::handleFailure, ::handleClosedQueue) },
            CloseQueueById.Params(idQueue),
            this.coroutineScope
        )
    }


    private fun handleClosedQueue(queue: Queue) {
            //_screenState = ScreenState.Render(ManagmentQueueScreenState.ClosedQueue(queue))
    }

}