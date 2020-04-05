package com.qflow.main.views.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.qflow.main.core.ScreenState
import com.qflow.main.domain.local.database.user.UserDB
import com.qflow.main.domain.local.database.AppDatabase
import com.qflow.main.domain.server.models.QueueServerModel
import com.qflow.main.usecases.user.LoginCase
import com.qflow.main.views.screenstates.LoginFragmentScreenState
import com.qflow.main.views.screenstates.ProfileFragmentScreenState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job


/**
 * Old ViewModel for the profileFragment
 * */
class ProfileViewModel(
    private val appDatabase: AppDatabase
) : ViewModel() {
    private lateinit var  info:QueueServerModel
    private val _screenState: MutableLiveData<ScreenState<ProfileFragmentScreenState>> =
        MutableLiveData()
    val screenState: LiveData<ScreenState<ProfileFragmentScreenState>>
        get() = _screenState

    private var job = Job()
    private var coroutineScope = CoroutineScope(Dispatchers.Default + job)

    //?¿

    private fun handleUserCreated(id: String) {
        this._screenState.value =
            ScreenState.Render(ProfileFragmentScreenState.AccessProfile(id))
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}
