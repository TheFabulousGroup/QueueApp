package com.qflow.main.views.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.qflow.main.core.BaseViewModel
import com.qflow.main.core.ScreenState
import com.qflow.main.domain.local.database.user.UserDB
import com.qflow.main.domain.local.database.AppDatabase
import com.qflow.main.domain.local.models.Queue
import com.qflow.main.usecases.creator.FetchAdminQueueNames
import com.qflow.main.usecases.user.LoginCase
import com.qflow.main.views.screenstates.HomeFragmentScreenState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.koin.core.KoinComponent

class HomeViewModel(
    private val fetchAdminQueueNames: FetchAdminQueueNames
) :  BaseViewModel(), KoinComponent {

    private val _currentUser = MutableLiveData<UserDB>()
    val currentUserDB: LiveData<UserDB>
        get() = _currentUser

    private val _screenState: MutableLiveData<ScreenState<HomeFragmentScreenState>> = MutableLiveData()
    val screenState: LiveData<ScreenState<HomeFragmentScreenState>>
        get() = _screenState

    private var job = Job()
    private var coroutineScope = CoroutineScope(Dispatchers.Default + job)

    fun getQueues(idUser: String): List<Queue> {
        fetchAdminQueueNames.execute(
            { it.either(::handleFailure, ::handleQueuesObtained) },
            FetchAdminQueueNames.Params(idUser),
            this.coroutineScope
        )
    }

    //Llamar a renderStateQueue

    private fun handleQueuesObtained(id: String) {
        this._screenState.value =
            ScreenState.Render(HomeFragmentScreenState.AccessHome(id))
    }

}
