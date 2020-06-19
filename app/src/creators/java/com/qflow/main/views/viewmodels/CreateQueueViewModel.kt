package com.qflow.main.views.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.qflow.main.core.BaseViewModel
import com.qflow.main.core.ScreenState
import com.qflow.main.core.ScreenState.Loading
import com.qflow.main.usecases.queue.CreateQueue
import com.qflow.main.views.screenstates.CreateQueueScreenState
import com.qflow.main.views.screenstates.HomeFragmentScreenState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.koin.core.KoinComponent

class CreateQueueViewModel(
    private val createQueue: CreateQueue
) : BaseViewModel(), KoinComponent {

    private val _screenState: MutableLiveData<ScreenState<HomeFragmentScreenState>> =
        MutableLiveData()
    val screenState: LiveData<ScreenState<HomeFragmentScreenState>>
        get() = _screenState

    private var job = Job()
    private var coroutineScope = CoroutineScope(Dispatchers.Default + job)

    fun createQueueInDatabase(
        nameCreateQueue: String,
        queueDescription: String,
        capacity: Int,
        businessAssociated: String,
        avgServiceTime: Int
    ) {
        _screenState.value = Loading
        //Execute create queue
        createQueue.execute(
            { it.either(::handleFailure, ::handleQueueCreated) },
            CreateQueue.Params(
                nameCreateQueue, queueDescription,
                capacity, businessAssociated, avgServiceTime
            ), this.coroutineScope
        )

    }

    private fun handleQueueCreated(id: String) {
        this._screenState.value =
            ScreenState.Render(HomeFragmentScreenState.QueueCreatedCorrectly(id))
    }


}
