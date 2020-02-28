package com.qflow.main.views.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.qflow.main.domain.local.database.user.User
import com.qflow.main.domain.local.database.AppDatabase

class ProfileViewModel(
    private val appDatabase: AppDatabase
) : ViewModel() {

    private val _currentUser = MutableLiveData<User>()
    val currentUser: LiveData<User>
        get() = _currentUser



    //TODO implement useCase that recovers currentUser
//    private var viewModelJob = Job()
//    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
//    init{
//        uiScope.launch {
//            executeGetCurrentUser()
//        }
//    }

//    private suspend fun executeGetCurrentUser() {
//        return withContext(Dispatchers.IO) {
//            _currentUser.postValue(appDatabase.userDatabaseDao.getCurrentUser())
//        }
//    }

}