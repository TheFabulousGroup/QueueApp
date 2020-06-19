package com.qflow.main.views.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.qflow.main.core.BaseViewModel
import com.qflow.main.core.ScreenState
import com.qflow.main.core.ScreenState.Loading
import com.qflow.main.domain.local.SharedPrefsRepository
import com.qflow.main.domain.local.database.user.UserDB
import com.qflow.main.domain.local.models.Queue
import com.qflow.main.usecases.queue.FetchQueuesByUser
import com.qflow.main.views.screenstates.HomeFragmentScreenState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.koin.core.KoinComponent

class HomeViewModel(
    private val fetchQueuesByUser: FetchQueuesByUser,
    private val sharedPrefsRepository: SharedPrefsRepository
) : BaseViewModel(), KoinComponent {

    private val _currentUser = MutableLiveData<UserDB>()
    val currentUserDB: LiveData<UserDB>
        get() = _currentUser

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

    fun logout() {
        sharedPrefsRepository.putUserToken(null)
    }

}
