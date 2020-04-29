package com.qflow.main.views.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.qflow.main.core.BaseViewModel
import com.qflow.main.core.ScreenState
import com.qflow.main.domain.local.database.user.UserDB
import com.qflow.main.domain.local.models.Queue
import com.qflow.main.usecases.creator.FetchAdminActiveQueues
import com.qflow.main.usecases.queue.FetchQueueById
import com.qflow.main.views.screenstates.HomeFragmentScreenState
import com.qflow.main.views.screenstates.InfoQueueScreenState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.koin.core.KoinComponent

class InfoQueueViewModel(
    private val fetchQueueById: FetchQueueById
) : BaseViewModel(), KoinComponent {

    private val _screenState: MutableLiveData<ScreenState<InfoQueueScreenState>> =
        MutableLiveData()
    val screenState: LiveData<ScreenState<InfoQueueScreenState>>
        get() = _screenState

    private var job = Job()
    private var coroutineScope = CoroutineScope(Dispatchers.Default + job)

    private lateinit var queueObtained: Queue

    fun fetchQueueById(idQueue: Int) {
        /*return when(val res = fetchQueueById.execute())
            is Either.Left ->*/
        var queue: Queue

        fetchQueueById.execute(
            { it.either(::handleFailure, ::handleQueueObtained) },
            FetchQueueById.Params(idQueue),
            this.coroutineScope
        )
    }

    private fun handleQueueObtained(queue: Queue) {
        this._screenState.value = ScreenState.Render(InfoQueueScreenState.QueueObtained(queue))
    }

}
